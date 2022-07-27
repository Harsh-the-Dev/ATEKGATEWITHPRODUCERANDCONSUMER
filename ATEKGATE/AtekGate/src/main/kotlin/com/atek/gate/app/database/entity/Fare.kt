package com.atek.gate.app.database.entity

data class Fare(
    val id: Int,
    val fare_table_id: Int,
    val source: Int,
    val destination: Int,
    val fare: Double,
    val fare_version: String,
)
