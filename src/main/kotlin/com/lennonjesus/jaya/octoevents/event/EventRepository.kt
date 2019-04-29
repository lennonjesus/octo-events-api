package com.lennonjesus.jaya.octoevents.event

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.KoinComponent
import javax.sql.DataSource

object Events : Table() {
//    private val id = integer("id")
    internal val action = varchar("action", length = 50)
    internal val issue = integer("issue_id")

    fun toDomain(row: ResultRow, issue: Issue): Event {
        return Event(
            action = row[action],
            issue = issue
        )
    }

}

object Issues : Table() {

    internal val id = integer("id")
    internal val title = varchar("title", length = 256)
    internal val body = varchar("body", length = 1000)
    internal val state = varchar("state", length = 50)
    internal val createdAt = varchar("created_at", length = 50)
    internal val updatedAt = varchar("updated_at", length = 50)

    fun toDomain(row: ResultRow): Issue {
        return Issue(
            id = row[id],
            title = row[title],
            body = row[body],
            state = row[state],
            createdAt = row[createdAt],
            updatedAt = row[updatedAt]
        )
    }

}

class EventRepository(private val dataSource: DataSource) : KoinComponent {

    init {
        transaction(Database.connect(dataSource)) {
            SchemaUtils.create(Events)
            SchemaUtils.create(Issues)
        }
    }

    fun save(event: Event) {
        transaction(Database.connect(dataSource)) {
            Events.insert {row ->
                row[action] = event.action
                row[issue] = event.issue.id
            }

            Issues.insert {row ->
                val issue = event.issue

                row[id] = issue.id
                row[title] = issue.title
                row[body] = issue.body
                row[state] = issue.state
                row[createdAt] = issue.createdAt
                row[updatedAt] = issue.updatedAt

            }
        }
    }

    fun listByIssueId(issueId: Int): List<Event> {
        return transaction(Database.connect(dataSource)) {
            Events.join(Issues, JoinType.INNER, additionalConstraint = { Events.issue eq Issues.id })
                .select {
                    Issues.id eq issueId
                }
                .map { Events.toDomain(it, Issues.toDomain(it)) }
        }
    }

}