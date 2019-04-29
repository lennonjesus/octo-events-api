package com.lennonjesus.jaya.octoevents.event

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.KoinComponent
import javax.sql.DataSource

object Events : Table() {
    val action = varchar("name", length = 50)

    fun toDomain(row: ResultRow): Event {
        return Event(action = row[action])
    }

}

class EventRepository(private val dataSource: DataSource) : KoinComponent {

    init {
        transaction(Database.connect(dataSource)) {
            SchemaUtils.create(Events)
        }
    }

    fun list(): List<Event> {
        return transaction(Database.connect(dataSource)) {
            Events.selectAll().map {
                Events.toDomain(it)
            }
        }
    }

}