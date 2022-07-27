package com.atek.gate.app.event

import com.atek.gate.data.model.event.SocketChat
import com.atek.gate.utils.Constants

data class SocketEvent(
    val event: Constants.SOCKET,
    val message: SocketChat
)