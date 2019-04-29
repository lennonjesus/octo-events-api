package com.lennonjesus.jaya.octoevents.event

import io.javalin.Context

class EventController(private val eventService: EventService) {

    fun list(ctx: Context) {
        ctx.json(eventService.list())
    }

}