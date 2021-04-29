package com.dylanlonglands.routes

import com.dylanlonglands.models.Order
import com.dylanlonglands.models.orders
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.routing

fun Route.listOrders() {
    get("/order") {
        if (orders.isNotEmpty()) {
            call.respond(orders)
        } else {
            call.respondText("No orders found", status = HttpStatusCode.NotFound)
        }
    }
}

fun Route.getOrder() {
    get("/order/{number}") {
        val number = call.parameters["number"] ?: return@get call.respondText(
            "Missing or malformed order number",
            status = HttpStatusCode.BadRequest
        )
        val order =
            orders.find { it.number == number } ?: return@get call.respondText(
                "No matching order number for $number",
                status = HttpStatusCode.NotFound
            )
        call.respond(order)
    }
}

fun Route.getOrderTotal() {
    get("/order/{number}/total") {
        val number = call.parameters["number"] ?: return@get call.respondText(
            "Missing or malformed order number",
            status = HttpStatusCode.BadRequest
        )
        val order =
            orders.find { it.number == number } ?: return@get call.respondText(
                "No matching order number for $number",
                status = HttpStatusCode.NotFound
            )
        val totalCost = order.contents.map {it.price * it.quantity}
        call.respond(totalCost)
    }
}

fun Route.createOrder() {
    post("/order") {
        val order = call.receive<Order>()
        orders.add(order)
        call.respondText("Your order has been placed", status = HttpStatusCode.Created)
    }
}

fun Application.registerOrderRoutes() {
    routing {
        listOrders()
        getOrder()
        getOrderTotal()
        createOrder()
    }
}