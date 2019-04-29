package com.lennonjesus.jaya.octoevents.event

import org.koin.core.KoinComponent

class EventService(private val eventRepository: EventRepository) : KoinComponent {

    fun list(): List<Event> {
        return eventRepository.list()
    }

}