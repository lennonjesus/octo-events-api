package com.lennonjesus.jaya.octoevents.event

import org.koin.core.KoinComponent

class EventRepository : KoinComponent {

    private val events = listOf<Event>(
        Event(action = "Beber"),
        Event(action = "Comer"),
        Event(action = "Programar"),
        Event(action = "Dormir")
    )

    fun list(): List<Event> {
        return events
    }



}