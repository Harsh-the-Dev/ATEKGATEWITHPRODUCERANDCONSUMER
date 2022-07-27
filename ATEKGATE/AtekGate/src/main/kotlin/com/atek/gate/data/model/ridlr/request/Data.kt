package com.atek.gate.data.model.ridlr.request

data class Data(
    val transactionId: String,
    val master_txn_id: String,
    val equipment_id: String,
    val support_type: String,
    val qr_type: String,
    val validation_type: String,
    val validation_station_id: String,
    val scan_time: String,
    val pass_id: String,
)
