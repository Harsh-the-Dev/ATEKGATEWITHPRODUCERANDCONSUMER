package com.atek.gate.app.database.entity

data class Equipment(
    val equipment_id: String,
    val eq_type_id: Int,
    val gateDirection: String,
    val end_date: String?,
    val status: String,
    val station_id: Int
)
