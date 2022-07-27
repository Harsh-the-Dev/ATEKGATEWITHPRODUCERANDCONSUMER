package com.atek.gate.data.listener

import com.atek.gate.app.event.AppEvent
import com.atek.gate.app.event.TransData
import com.atek.gate.utils.Constants
import com.pi4j.io.serial.SerialDataEvent
import com.pi4j.io.serial.SerialDataEventListener
import org.greenrobot.eventbus.EventBus

class GateCommandListener : SerialDataEventListener {

    override fun dataReceived(event: SerialDataEvent?) {

        if (event != null) {

            val eventData = event.hexByteString

            if (eventData.contains(Constants.ENTRY_GATE_RESPONSE)) {
                EventBus.getDefault().post(AppEvent(Constants.EVENT.GATE_OPENED))
                TransData.transGate = Constants.GATE.ENTRY_GATE
            }

            if (eventData.contains(Constants.EXIT_GATE_RESPONSE)) {
                EventBus.getDefault().post(AppEvent(Constants.EVENT.GATE_OPENED))
                TransData.transGate = Constants.GATE.EXIT_GATE
            }

        } else {

            EventBus.getDefault().post(AppEvent(Constants.EVENT.ERROR))
            TransData.error = "Empty gate response !!"

        }

    }

}