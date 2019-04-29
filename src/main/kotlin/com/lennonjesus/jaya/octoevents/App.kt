package com.lennonjesus.jaya.octoevents

import com.lennonjesus.jaya.octoevents.event.EventController
import com.lennonjesus.jaya.octoevents.event.eventModule
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject

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
        val app = Javalin.create().apply {
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
            error(404) { ctx -> ctx.result("Not found") }
        }.start(port)

        // app endpoints
        app.routes {

            // Github webhook entrypoint
            post("/hooks/github") { ctx ->
                println(ctx.body())
            }

            path("issues/:id/events") {
                get(eventController::list)
            }

        }

        return app
    }
}