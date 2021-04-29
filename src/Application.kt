package com.dylanlonglands

import com.dylanlonglands.routes.registerCustomerRoutes
import com.dylanlonglands.routes.registerOrderRoutes
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.serialization.json

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    registerCustomerRoutes()
    registerOrderRoutes()
}

