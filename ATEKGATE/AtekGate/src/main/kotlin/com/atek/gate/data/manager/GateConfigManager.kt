package com.atek.gate.data.manager

import com.atek.gate.app.AtekGate
import com.atek.gate.app.database.AtekGateDB
import com.atek.gate.app.database.dao.EquipmentDao
import com.atek.gate.app.database.dao.FareDao
import com.atek.gate.app.database.dao.ProductDao
import com.atek.gate.app.event.AppEvent
import com.atek.gate.app.event.TransData
import com.atek.gate.data.model.config.ConfigResponse
import com.atek.gate.data.repository.ConfigRepository
import com.atek.gate.utils.Constants
import com.atek.gate.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.greenrobot.eventbus.EventBus

class GateConfigManager {

    suspend fun startConfiguration() {

        val deviceIpParams: MutableMap<String, String> = HashMap()
        deviceIpParams["device_ip"] = "10.13.3.166"

        when (val configResponse = ConfigRepository.getConfigurations(deviceIpParams)) {
            is Response.Success -> {
                if (configResponse.data != null) {
                    println("Configuration Success")
                    onSuccess(configResponse.data)
                } else {
                    println("Configuration Failed")
                    onError(configResponse.message ?: "Unhandled exception occurs!", Constants.ERROR.CONFIG_ERROR)
                }
            }
            is Response.Error -> onError(configResponse.message ?: "Unhandled exception occurs!", Constants.ERROR.CONFIG_ERROR)
        }
    }

    suspend fun updateConfiguration() {

        val deviceIpParams: MutableMap<String, String> = HashMap()
        deviceIpParams["device_ip"] = "10.13.3.166"

        when (val configResponse = ConfigRepository.getConfigurations(deviceIpParams)) {
            is Response.Success -> {
                if (configResponse.data != null) {
                    onSuccess(configResponse.data)
                } else {
                    onError(configResponse.message ?: "Unhandled exception occurs!", Constants.ERROR.UPDATE_ERROR)
                }
            }
            is Response.Error -> onError(configResponse.message ?: "Unhandled exception occurs!", Constants.ERROR.UPDATE_ERROR)
        }

    }

    private suspend fun onSuccess(data: ConfigResponse) {

        runBlocking(Dispatchers.IO) {

            AtekGateDB.createTables()

            if (data.equipment != null)
            {
                EquipmentDao.addEquipment(data.equipment)
                AtekGate.equipment = data.equipment
            }
            else if (data.fares != null)
            {
                FareDao.addFares(data.fares)
            }
            else if (data.products != null)
            {
                ProductDao.addProducts(data.products)
            }

        }

        EventBus.getDefault().post(AppEvent(Constants.EVENT.CONFIG_SUCCESS))

    }

    private fun onError(message: String, errorType: Constants.ERROR) {
        TransData.errorType = errorType
        TransData.error = message
        EventBus.getDefault().post(AppEvent(Constants.EVENT.ERROR))
    }

}