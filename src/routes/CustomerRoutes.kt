package com.dylanlonglands.routes

import com.dylanlonglands.models.Customer
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import com.dylanlonglands.models.customerStorage
import io.ktor.routing.*

fun Route.listCustomers() {
    get ("/customer") {
        if (customerStorage.isNotEmpty()) {
            call.respond(customerStorage)
        } else {
            call.respondText("No customers found", status = HttpStatusCode.NotFound)
        }
    }
}

fun Route.getCustomer() {
    get("/customer/{id}") {
        val id = call.parameters["id"] ?: return@get call.respondText(
            "Missing or malformed id",
            status = HttpStatusCode.BadRequest
        )
        val customer =
            customerStorage.find { it.id == id } ?: return@get call.respondText(
                "No customer found with id $id",
                status = HttpStatusCode.NotFound
            )
        call.respond(customer)
    }
}

fun Route.createCustomer() {
    post("/customer") {
        val customer = call.receive<Customer>()
        customerStorage.add(customer)
        call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
    }
}

fun Route.deleteCustomer() {
    delete("/customer/{id}") {
        val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
        if (customerStorage.removeIf { it.id == id }) {
            call.respondText("Customer: $id deleted", status = HttpStatusCode.Accepted)
        } else {
            call.respondText("Customer: $id not found", status = HttpStatusCode.NotFound)
        }
    }
}

fun Application.registerCustomerRoutes() {
    routing {
        listCustomers()
        getCustomer()
        createCustomer()
        deleteCustomer()
    }
}