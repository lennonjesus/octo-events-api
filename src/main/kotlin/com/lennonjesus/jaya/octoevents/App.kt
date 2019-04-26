package com.lennonjesus.jaya.octoevents

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.post
//import kotlin.text.toIntOrNull

fun main() {

    val issueRepository = IssueRepository()

    val port: Int = System.getenv("PORT")?.toIntOrNull() ?: 7000

    val app = Javalin.create().apply {
        exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
        error(404) { ctx -> ctx.json("not found") }
    }.start(port)


    app.routes {
        get("/issues") { ctx -> ctx.json("Hello World") }
        post("/issues") { ctx -> ctx.json("Hello World") }
        post("/hooks/github") { ctx ->
            println(ctx.body())
        }
    }



}