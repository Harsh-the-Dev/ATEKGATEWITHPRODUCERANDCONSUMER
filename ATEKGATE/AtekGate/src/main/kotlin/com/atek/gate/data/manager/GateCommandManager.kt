package com.atek.gate.data.manager

import com.atek.gate.app.AtekGate
import com.atek.gate.data.listener.GateCommandListener
import com.atek.gate.utils.Constants
import com.pi4j.io.serial.*
import kotlinx.coroutines.*
import java.io.IOException

class GateCommandManager {

    fun sendCommand(command: ByteArray) {

        // CREATING SERIAL
        val serial = SerialFactory.createInstance()

        // ADD LISTENER
        serial.addListener(GateCommandListener())

        // CREATING CONFIG
        val config = SerialConfig()
        config.device(Constants.ttyUSB0)
            .baud(Baud._38400)
            .dataBits(DataBits._8)
            .parity(Parity.NONE)
            .stopBits(StopBits._1)
            .flowControl(FlowControl.NONE)

        AtekGate.appScope.launch {

            try {

                // OPENING SERIAL PORT
                serial.open(config)

                // SEND COMMAND
                serial.write(command)
                delay(500)

                // CLOSING SERIAL
                serial.close()

            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }

    }

}