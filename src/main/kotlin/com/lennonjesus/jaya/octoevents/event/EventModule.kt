package com.lennonjesus.jaya.octoevents.event

import com.lennonjesus.jaya.octoevents.config.DatabaseConfig
import org.koin.dsl.module

val eventModule = module {
    single { EventController(get()) }
    single { EventService(get()) }
    single { EventRepository(get()) }
    single {
        // TODO: Recuperar a partir de um .properties
        DatabaseConfig("jdbc:h2:mem:api", "sa", "").getDataSource()
    }
}