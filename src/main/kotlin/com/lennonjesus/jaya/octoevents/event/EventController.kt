package com.lennonjesus.jaya.octoevents.event

import io.javalin.Context

class EventController(private val eventService: EventService) {

    fun save(ctx: Context) {
        eventService.save(ctx.body<Event>()).apply {
            ctx.status(201).json(mapOf("status" to "Successful created"))
        }
    }

    fun listByIssueId(ctx: Context) {
        ctx.json(eventService.listByIssueId(
            ctx.pathParam("id").toInt()
        ))
    }

    fun list(ctx: Context) {
        ctx.json(eventService.list())
    }

}