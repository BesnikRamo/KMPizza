package dev.tutorial.kmpizza.backend

import LocalSource
import api
import getKoinModule
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.routing.Routing
import io.ktor.serialization.json
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject

internal fun Application.module() {
    install(Koin) {
        modules(getKoinModule())
    }

    val localSource by inject<LocalSource>()

    install(Routing) {
        api(localSource)
    }

    install(ContentNegotiation) {
        json()
    }
}