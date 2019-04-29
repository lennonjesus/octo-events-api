package com.lennonjesus.jaya.octoevents

import com.lennonjesus.jaya.octoevents.event.Event
import com.lennonjesus.jaya.octoevents.event.Issue

val issueDummy = Issue(
    id = 438329209,
    title = "TDD com Kotlin",
    body = "Na próxima, tenho que começar pelos testes... :)",
    state = "open",
    createdAt = "2019-04-29T13:56:41Z",
    updatedAt = "2019-04-29T13:56:41Z"
)

val createdEventDummy = Event(
    action = "created",
    issue = issueDummy
)

val closedEventDummy = Event(
    action = "created",
    issue = issueDummy
)