package com.atek.gate.ui.activity

import Kafka.ProducerAndConsumer.senderAndReciver
import com.atek.gate.app.event.manager.runner
import com.atek.gate.app.AtekGate
import com.atek.gate.app.database.dao.EquipmentDao
import com.atek.gate.app.database.dao.TransactionDao
import com.atek.gate.app.event.AppEvent
import com.atek.gate.app.event.SocketEvent
import com.atek.gate.app.event.TransData
import com.atek.gate.data.model.QrData
import com.atek.gate.utils.Constants
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
class MainActivity : AtekGate() {

    init {
        console.box("ATEK GATE INITIATED")
        runner()
       senderAndReciver()
        //main function for running the kafka producer and consumer

        //Registering MainActivity for future events
        EventBus.getDefault().register(this)

        // Checking if device is configured or not
        val equipmentFromDB = runBlocking(Dispatchers.IO) {
            EquipmentDao.getEquipment()
        }

        if (equipmentFromDB == null) {
            console.box("GATE IS NOT CONFIGURED, STARTING CONFIG PROCESS")
            appScope.launch { gateConfigManager.startConfiguration() }
        } else {
            console.box("CHECKING FOR CONFIG UPDATE")
            screen = Constants.SCREENS.UPDATE
            appScope.launch { gateConfigManager.updateConfiguration() }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: AppEvent) {

        when (event.event) {

            /*
            * Configuration is successful and local DB
            * is populated following needs to be done.
            * 1. Start gate pulse
            * 2. Start gate scanners
            * 3. Change screen
            * 4. Set gate mode
            * 4. Start transaction service
            */
            Constants.EVENT.CONFIG_SUCCESS -> {
                console.box("CONFIGURATION SUCCESSFULLY")
                console.box("STARTING GATE PULSE AND SCANNER")
                /*gatePulseManager.startGatePulse()
*/
                // Set gate mode
                /*when (equipment.gateDirection) {
                    "ENTRY_ONLY" -> gateCommandManager.sendCommand(Constants.ENTRY_ONLY)
                    "EXIT_ONLY" -> gateCommandManager.sendCommand(Constants.EXIT_ONLY)
                    else -> gateCommandManager.sendCommand(Constants.BI_DI)
                }*/

                /*gateScannerManager.startGateScanners()*/
                ridlrTransService.startTranscationService()
                screen = Constants.SCREENS.MAIN
            }

            /*
            * After QR is scanned, and it's validated
            * following needs to be done
            * 1. Send gate open command (Entry / Exit)
            * 2. Change screen
            */
            Constants.EVENT.QR_SCANNED -> {
                screen = if (TransData.transScanner == Constants.SCANNER.SCANNED_AT_ENTRY) {
                    gateCommandManager.sendCommand(Constants.ENTRY_GATE_OPEN_CMD)
                    TransData.transType = Constants.ENTRY_TRANSACTION

                    Constants.SCREENS.ENTRY
                } else {
                    gateCommandManager.sendCommand(Constants.EXIT_GATE_OPEN_CMD)
                    TransData.transType = Constants.EXIT_TRANSACTION
                    Constants.SCREENS.EXIT
                }
            }

            /*
            * Gate opened successfully  now
            * following needs to be done
            * 1. Create transaction
            * 2. Change screen (A delay of 5s is applied
            *    because we don't have any method to determine
            *    weather gate is closed oe not).
            */
            Constants.EVENT.GATE_OPENED -> {
                createTransaction()
                runBlocking(Dispatchers.IO) {
                    delay(5000)
                    screen = Constants.SCREENS.MAIN
                }
            }

            /*
            * If error accrued show errors
            */
            Constants.EVENT.ERROR -> {

                screen = Constants.SCREENS.ERROR

                when (TransData.errorType) {

                    /*
                    * If configuration failed then retry
                    * after 3s until configuration is
                    * successful.
                    */
                    Constants.ERROR.CONFIG_ERROR -> {
                        runBlocking {
                            delay(3000)
                            screen = Constants.SCREENS.CONFIG
                            gateConfigManager.startConfiguration()
                        }
                    }

                    /*
                    * If update error accrued then
                    * config updated will be marked
                    * successful after 3s
                    */
                    Constants.ERROR.UPDATE_ERROR -> {
                        runBlocking {
                            delay(3000)
                            EventBus.getDefault().post(
                                AppEvent(
                                    Constants.EVENT.CONFIG_SUCCESS
                                )
                            )
                        }
                    }

                    /*
                    * If QR code is invalid show validation
                    * error and after 3s, change the screen
                    * to Main
                    */
                    Constants.ERROR.INVALID_QR -> {
                        runBlocking {
                            delay(3000)
                            screen = Constants.SCREENS.MAIN
                        }
                    }
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSocketEvent(event: SocketEvent) {
        when (event.event) {

            /*
            * After receiving message following
            * will be needed to done
            * 1. Check if message is for uh
            * 2. If message type is 1 then send command
            * 3. If message type is 2 then create transaction
            */
            Constants.SOCKET.MESSAGE_RECEIVED -> {

                appScope.launch {

                    println("Raghu")

                    for (equipment_id in event.message.equipment_ids) {

                        if (equipment_id == equipment.equipment_id) {

                            if (event.message.messageType == 1) {

                                when (event.message.command) {
                                    "ENTRY" -> gateCommandManager.sendCommand(Constants.ENTRY_ONLY)
                                    "EXIT" -> gateCommandManager.sendCommand(Constants.EXIT_ONLY)
                                    "BI-DI" -> gateCommandManager.sendCommand(Constants.BI_DI)
                                    "ENTRY_OPEN" -> gateCommandManager.sendCommand(Constants.ENTRY_GATE_OPEN_CMD)
                                    "EXIT_OPEN" -> gateCommandManager.sendCommand(Constants.EXIT_GATE_OPEN_CMD)
                                }

                            } else {

                                TransactionDao.addTransaction(
                                    Gson().fromJson(
                                        event.message.transaction,
                                        QrData::class.java
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
        }
    }

