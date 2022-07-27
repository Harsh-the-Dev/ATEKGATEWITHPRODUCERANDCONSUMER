package com.atek.gate.data.manager

import com.atek.gate.app.AtekGate
import com.atek.gate.data.listener.GateScannerListener
import com.atek.gate.utils.Constants
import com.pi4j.io.serial.*
import kotlinx.coroutines.*
import java.io.IOException
import java.util.Date

class GateScannerManager {

    fun startGateScanners() {

        val equipment = AtekGate.equipment

        when (equipment.gateDirection) {
            "ENTRY_ONLY" -> startEntryScanner()
            "EXIT_ENTRY" -> startExitScanner()
            else -> {
                startEntryScanner()
                startExitScanner()
            }
        }

    }

    private fun startEntryScanner() {

        AtekGate.console.box("STARING ENTRY GATE SCANNER")

        // CREATING SERIAL
        val serial = SerialFactory.createInstance()

        // ADD EVENT LISTENER
        serial.addListener(GateScannerListener(Constants.SCANNER.SCANNED_AT_ENTRY))

        // CREATING CONFIG
        val config = SerialConfig()
        config.device(Constants.ttyAMC0)
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
                    serial.write(Date().toString())
                    delay(500)
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

    private fun startExitScanner() {

        AtekGate.console.box("STARING EXIT GATE SCANNER")

        // CREATING SERIAL
        val serial = SerialFactory.createInstance()

        // ADD EVENT LISTENER
        serial.addListener(GateScannerListener(Constants.SCANNER.SCANNED_AT_EXIT))

        // CREATING CONFIG
        val config = SerialConfig()
        config.device(Constants.ttyAMC1)
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
                    serial.write(Date().toString())
                    delay(500)
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