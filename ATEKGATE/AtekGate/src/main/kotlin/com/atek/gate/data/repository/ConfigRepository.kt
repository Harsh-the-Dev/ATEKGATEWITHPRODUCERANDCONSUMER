package com.atek.gate.data.repository

import com.atek.gate.data.model.config.ConfigResponse
import com.atek.gate.data.model.ridlr.request.ScanEventRequest
import com.atek.gate.data.model.ridlr.response.ScanEventResponse
import com.atek.gate.utils.Response
import com.atek.gate.data.network.ApiController
import com.google.gson.Gson
import com.google.gson.JsonObject

object ConfigRepository {

    suspend fun getConfigurations(deviceIpParams: Map<String, String>): Response<ConfigResponse> {

        try {

            val response = ApiController.apiService.getConfigurations(deviceIpParams)

            return if (response.isSuccessful) {
                if (response.body() != null) {
                    Response.Success(response.body())
                } else {
                    Response.Error(response.message())
                }
            } else {
                Response.Error(response.message())
            }

        } catch (e: Exception) {
            e.printStackTrace()
            return Response.Error(e.message ?: "Unhandled exception")
        }

    }

    suspend fun sendScanEvent(scanEventRequest: ScanEventRequest): Response<ScanEventResponse> {

        try {

            val response = ApiController.apiService.sendScanEvent(scanEventRequest)

            println("RESPONSE " + Gson().toJson(response.body()))

            return if (response.isSuccessful) {
                if (response.body() != null) {
                    Response.Success(response.body())
                } else {
                    Response.Error(response.message())
                }
            } else {
                Response.Error(response.message())
            }

        } catch (e: Exception) {
            e.printStackTrace()
            return Response.Error(e.message ?: "Unhandled exception")
        }

    }

}