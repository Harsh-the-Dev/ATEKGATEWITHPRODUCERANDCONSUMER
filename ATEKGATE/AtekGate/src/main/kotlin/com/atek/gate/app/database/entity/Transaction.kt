package com.atek.gate.app.database.entity

data class Transaction(
    val id: Int? = null,
    val stan: String,
    val request: String,
    val media_type_id: Int,
    val product_type_id: Int,
    val pass_id: Int,
    val ms_qr_no: String,
    val sl_qr_no: String,
    val trans_type: Int,
    val settled: Boolean,
)