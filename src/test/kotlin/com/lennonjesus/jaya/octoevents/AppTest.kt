package com.lennonjesus.jaya.octoevents

import com.lennonjesus.jaya.octoevents.event.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.javalin.Javalin
import junit.framework.TestCase
import khttp.get
import khttp.responses.Response
import org.h2.tools.Server
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.json.JSONObject
import org.junit.Ignore
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin


class AppTest : KoinComponent {

    // TODO
    //  - reestruturar classes de teste
    //  - criar testes unitarios
    //  - usar mockk?
    //  - Refatorar App.kt para usar melhor no test

    companion object {

        private lateinit var app : Javalin

        private const val url = "http://localhost:7000"

        private val dataSource = HikariConfig().let { config ->
            config.jdbcUrl = "jdbc:h2:mem:api"
            config.username = "sa"
            config.password = ""
            HikariDataSource(config)
        }

        @BeforeAll @JvmStatic
        fun setUp() {

            startKoin {
                modules(eventModule)
            }

            app = JavalinApp(7000).init()

            Server.createPgServer().start()

            transaction(Database.connect(dataSource)) {
                SchemaUtils.drop(Events)
                SchemaUtils.drop(Issues)

                SchemaUtils.create(Events)
                SchemaUtils.create(Issues)

            }
        }

        @AfterAll @JvmStatic
        fun tearDown() {
            app.stop()
        }
    }

    @Test
    fun `It should accept webhook and create data`() {

        val json = """
            {
              "action": "opened",
              "issue": {
                "id": 42,
                "title": "Another issue",
                "state": "open",
                "created_at": "2019-04-29T13:56:41Z",
                "updated_at": "2019-04-29T13:56:41Z",
                "body": "This is another test issue"
              }
            }
            """

        val response = khttp.post(url = "$url/hooks/github", data = JSONObject(json))

        val responseJson : JSONObject = response.jsonObject

        assertEquals(201, response.statusCode)
        assertEquals("Successful created", responseJson["status"])
    }

    @Test
    fun `It should get events list by issue id when events exists`() {

        transaction(Database.connect(dataSource)) {
            Events.insert {row ->
                row[action] = createdEventDummy.action
                row[issue] = createdEventDummy.issue.id
            }

            Events.insert {row ->
                row[action] = closedEventDummy.action
                row[issue] = closedEventDummy.issue.id
            }

            Issues.insert {row ->
                row[id] = issueDummy.id
                row[title] = issueDummy.title
                row[body] = issueDummy.body
                row[state] = issueDummy.state
                row[createdAt] = issueDummy.createdAt
                row[updatedAt] = issueDummy.updatedAt

            }
        }

        val response : Response = get("$url/issues/438329209/events")

        val events = response.text.fromJson<List<Event>>()

        assertEquals(200, response.statusCode)
        assertEquals(2, events.size)
        assertEquals(events[0].action, createdEventDummy.action)
        assertEquals(events[0].issue.id, createdEventDummy.issue.id)
        assertEquals(events[0].action, closedEventDummy.action)
        assertEquals(events[0].issue.id, closedEventDummy.issue.id)
    }

    @Test
    fun `It should get empty events list when doesn't exists events with issue id`() {
        val response : Response = get("$url/issues/0000000/events")

        val events = response.text.fromJson<List<Event>>()

        assertEquals(200, response.statusCode)
        assertTrue(events.isEmpty())
    }

    @Test
    fun `It should get not found message if resource doesnt exists`() {
        val response : Response = get("$url/error")
        assertEquals(404, response.statusCode)
        assertEquals("Not found", response.text)
    }

}
