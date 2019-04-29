package com.lennonjesus.jaya.octoevents

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

inline fun<reified T : Any>String.fromJson(): T= jacksonObjectMapper().readValue(this)