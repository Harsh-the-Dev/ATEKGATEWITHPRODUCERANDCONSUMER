package com.atek.gate.data.network

import com.atek.gate.data.model.config.ConfigResponse
import com.atek.gate.data.model.ridlr.request.ScanEventRequest
import com.atek.gate.data.model.ridlr.response.ScanEventResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FieldMap

import retrofit2.http.FormUrlEncoded

import retrofit2.http.POST


interface ApiService {

    @POST("/atek_backend/v2/getConfigurations.php")
    @FormUrlEncoded
    suspend fun getConfigurations(@FieldMap DeviceIpParams: Map<String, String>): Response<ConfigResponse>

    @POST("/postScanEvent")
    suspend fun sendScanEvent(@Body scanEventRequest: ScanEventRequest): Response<ScanEventResponse>

}