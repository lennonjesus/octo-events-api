package com.lennonjesus.jaya.octoevents

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.post

fun main() {

    val issueRepository = IssueRepository()

    val app = Javalin.create().apply {
        exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
        error(404) { ctx -> ctx.json("not found") }
    }.start(7000)


    app.routes {
        get("/issues") { ctx -> ctx.json("Hello World") }
        post("/issues") { ctx -> ctx.json("Hello World") }
    }



}