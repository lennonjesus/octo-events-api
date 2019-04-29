package com.lennonjesus.jaya.octoevents

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.lennonjesus.jaya.octoevents.event.EventController
import com.lennonjesus.jaya.octoevents.event.eventModule
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.json.JavalinJackson
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject
import java.text.SimpleDateFormat

fun main() {

    startKoin {
        modules(eventModule)
    }

    JavalinApp(7000).init()
}

class JavalinApp(private val port: Int) : KoinComponent {

    private val eventController: EventController by inject()


    fun init(): Javalin {

        // get heroku port or uses default (for local environment)
        val port: Int = System.getenv("PORT")?.toIntOrNull() ?: port

        // starts Javalin
        val app = Javalin.create()
            .also {
                this.configureMapper()
            }
            .apply {
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
            error(404) { ctx -> ctx.result("Not found") }
        }
            .maxBodySizeForRequestCache(100000) // FIXME remover
            .start(port)

        // app endpoints
        app.routes {

            // Github webhook entrypoint
            path("/hooks/github") {
                post(eventController::save)
            }

            path("issues/:id/events") {
                get(eventController::listByIssueId)
            }

            // soh pra facilitar a vida...
            path("events") {
                get(eventController::list)
            }

        }


        return app
    }

    private fun configureMapper() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        JavalinJackson.configure(
            jacksonObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(dateFormat)
                .configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, true)
        )
    }
}

