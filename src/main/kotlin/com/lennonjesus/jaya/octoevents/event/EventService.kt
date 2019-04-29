package com.lennonjesus.jaya.octoevents.event

import org.koin.core.KoinComponent

class EventService(private val eventRepository: EventRepository) : KoinComponent {

    fun save(event: Event) {
        eventRepository.save(event)
    }

    fun listByIssueId(issueId: Int): List<Event> {
        return eventRepository.listByIssueId(issueId)
    }

    fun list(): List<Event> {
        return eventRepository.list()
    }

}