package com.atek.gate.data.model.event

data class SocketChat(
    /*
    * 1. Command
    * 2. Transaction
    */
    val messageType: Int,
    val equipment_ids: List<String>,
    val transaction: String?,
    val command: String?
)
