package com.dylanlonglands.models

val orders = mutableListOf<Order>()

data class Order(val number: String, val contents: List<OrderItem>)

data class OrderItem(val item: String, val quantity: Int, val price: Double)