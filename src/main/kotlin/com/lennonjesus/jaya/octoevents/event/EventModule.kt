package com.lennonjesus.jaya.octoevents.event

import org.koin.dsl.module

val eventModule = module {
    single { EventController(get()) }
    single { EventService(get()) }
    single { EventRepository() }
}