package com.atek.gate.data.model.ridlr.response

data class Data(
    val equipment_id: String,
    val pass_id: Int,
    val qr_type: Int,
    val scan_time: String,
    val support_type: Int,
    val transactionId: String,
    val valid: String,
    val validation_station_id: Int,
    val validation_type: String
)