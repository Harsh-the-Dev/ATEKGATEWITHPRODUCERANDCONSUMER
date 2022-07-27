package com.atek.gate.data.model.ridlr.response

data class ScanEventResponse(
    val code: Int,
    val `data`: Data,
    val message: String,
    val status: String
)