package com.atek.gate.data.manager

import com.atek.gate.app.AtekGate
import com.atek.gate.utils.Constants
import com.atek.gate.utils.Constants.PULSE
import com.pi4j.io.serial.*
import kotlinx.coroutines.*
import java.io.IOException

class GatePulseManager {

    fun startGatePulse() {

        AtekGate.console.box("GATE PULSE STARTED")

        // CREATING SERIAL
        val serial = SerialFactory.createInstance()

        // CREATING CONFIG
        val config = SerialConfig()
        config.device(Constants.ttyUSB0)
            .baud(Baud._38400)
            .dataBits(DataBits._8)
            .parity(Parity.NONE)
            .stopBits(StopBits._1)
            .flowControl(FlowControl.NONE)

        CoroutineScope(Dispatchers.IO).launch {

            try {

                // OPENING SERIAL PORT
                serial.open(config)

                // KEEPING CONNECTION ALIVE
                while (isActive) {
                    serial.write(PULSE)
                    delay(3000)
                }

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