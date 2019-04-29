package com.lennonjesus.jaya.octoevents.event

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown=true)
data class Issue(
    var id: Int,
    var title: String,
    var body: String,
    var state: String,
    @JsonProperty("created_at") var createdAt: String,
    @JsonProperty("updated_at") var updatedAt: String
)