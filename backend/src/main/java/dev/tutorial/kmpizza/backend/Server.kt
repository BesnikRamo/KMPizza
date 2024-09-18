package dev.tutorial.kmpizza.backend

import io.ktor.application.Application
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty


fun main() {
    embeddedServer(Netty, port = 9090, module = Application::module).start(wait = true)
}

/*
fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).apply {
        start()
    }
}*/
