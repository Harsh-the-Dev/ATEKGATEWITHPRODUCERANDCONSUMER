package com.atek.gate.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.atek.gate.app.database.dao.TransactionDao
import com.atek.gate.app.database.entity.Equipment
import com.atek.gate.app.event.TransData
import com.atek.gate.data.manager.*
import com.atek.gate.data.service.RidlrTransService
import com.atek.gate.utils.Constants
import com.google.gson.GsonBuilder
import com.pi4j.util.Console
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class AtekGate {

    var screen by mutableStateOf(Constants.SCREENS.CONFIG)

    companion object {

        // Base
        lateinit var console: Console
        lateinit var equipment: Equipment

        // Managers
        lateinit var gatePulseManager: GatePulseManager
        lateinit var gateCommandManager: GateCommandManager
        lateinit var gateConfigManager: GateConfigManager
        lateinit var gateScannerManager: GateScannerManager
        lateinit var gateValidatorManager: GateValidatorManager
        lateinit var ridlrTransService: RidlrTransService

        // Moshi Instance
        var gson = GsonBuilder()
            .setLenient()
            .create()

        // Coroutine
        val appScope = CoroutineScope(Dispatchers.IO)

        // Socket
//        lateinit var gateClientManager: GateClientManager

    }

    init {

        // Base
        console = Console()

        //Managers
        gatePulseManager = GatePulseManager()
        gateCommandManager = GateCommandManager()
        gateConfigManager = GateConfigManager()
        gateScannerManager = GateScannerManager()
        gateValidatorManager = GateValidatorManager()
        ridlrTransService = RidlrTransService()

        // Socket
//        initSocket()

    }

    /*
    * Initializing gate as a
    * client for socket messaging
    */
//    private fun initSocket() {
//        gateClientManager = GateClientManager()
//        gateClientManager.listenForMessage()
//        gateClientManager.sendMessage(
//            "10.13.3.166" + " : Joined Room"
//        )
//    }

    /*
    * Create transaction
    */
    fun createTransaction() {
        appScope.launch {
            TransactionDao.addTransaction(
                TransData.qrData
            )
        }
        /*gateClientManager.sendMessage(
            Gson().toJson(
                SocketChat(
                    1,
                 //Equipment.add(""),
                    "Socket",
                    "qrData"
                )
            )
        )*/
    }

}