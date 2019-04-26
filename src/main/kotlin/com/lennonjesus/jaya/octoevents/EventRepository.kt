package com.lennonjesus.jaya.octoevents

import java.util.concurrent.atomic.AtomicInteger

class IssueRepository {

    private val issues = listOf<Event>(
        Event(),
        Event(),
        Event(),
        Event(),
        Event()
    )

    private var lastId: AtomicInteger = AtomicInteger(issues.size - 1)

    fun save(event: Event) {
        val id = lastId.incrementAndGet()
        issues + Event()
    }

    fun listEvents(): List<Event> {
        return issues
    }

}