package com.lennonjesus.jaya.octoevents.event

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown=true)
data class Event(var action : String, var issue: Issue)

// TODO ver melhor pratica para equals e hashCode em Kotlin
