package com.atek.gate.data.listener

import com.atek.gate.app.AtekGate
import com.atek.gate.app.event.AppEvent
import com.atek.gate.app.event.TransData
import com.atek.gate.data.model.QrData
import com.atek.gate.utils.Constants
import com.atek.gate.utils.GCrypt
import com.pi4j.io.serial.SerialDataEvent
import com.pi4j.io.serial.SerialDataEventListener
import org.greenrobot.eventbus.EventBus

class GateScannerListener(private val scannedAt: Constants.SCANNER) : SerialDataEventListener {

    override fun dataReceived(event: SerialDataEvent?) {

        if (scannedAt == Constants.SCANNER.SCANNED_AT_ENTRY) {
            AtekGate.console.box("QR SCANNED AT ENTRY")
        } else {
            AtekGate.console.box("QR SCANNED AT EXIT")
        }

        if (event != null) {

            val encodedData = event.asciiString
            AtekGate.console.box("QR STRING [ENCODED] : $encodedData")

            try {

                val qrData = decodeHandler(encodedData.trim())
                AtekGate.console.print("QR STRING [PARSED] : $qrData")

                if (qrData != null) {
                    // Set trans dta
                    TransData.qrData = qrData
                    TransData.transScanner = scannedAt
                } else {
                    EventBus.getDefault().post(AppEvent(Constants.EVENT.ERROR))
                    TransData.errorType = Constants.ERROR.INVALID_QR
                    TransData.error = "Unable to parse qr code !!"
                }

                if (AtekGate.gateValidatorManager.isValidData()) {
                    EventBus.getDefault().post(AppEvent(Constants.EVENT.QR_SCANNED))
                }

            } catch (e: Exception) {
                e.printStackTrace()
                EventBus.getDefault().post(AppEvent(Constants.EVENT.ERROR))
                TransData.errorType = Constants.ERROR.INVALID_QR
                TransData.error = e.message ?: "Unable to decode qr code !!"
            }
        }
    }

    private fun decodeHandler(encodedData: String?): QrData? {

        if (encodedData != null) {

            val encodedDataArray = encodedData.trim().split('~')
            val decodedDataArray = arrayOfNulls<String>(2)

            try {

                val temDecodedData = GCrypt.decrypt(
                    encodedDataArray[0],
                    1
                )

                if (temDecodedData != null) {

                    decodedDataArray[0] = String(temDecodedData).trim()
                    val qrData = AtekGate.gson.fromJson(decodedDataArray[0], QrData::class.java!!)
                    AtekGate.console.print("QR STRING [DECODED] : ${decodedDataArray[0]}")

                    if (encodedDataArray.size > 1) {

                        var encodedDateTime = encodedDataArray[encodedDataArray.size - 1]

                        if (encodedDateTime.trim() == "") {
                            encodedDateTime = encodedDataArray[encodedDataArray.size - 2]
                        }

                        val temDecodedDateTimeData = GCrypt.decrypt(
                            encodedDateTime,
                            1
                        )

                        if (temDecodedDateTimeData != null) {

                            decodedDataArray[1] = String(temDecodedDateTimeData)

                            val extraData = decodedDataArray[1]?.split("\\|".toRegex())

                            qrData?.ec = extraData?.get(0)?.trim()
                            AtekGate.console.print("EXTRA DATA [CHILD EXPIRY] : ${extraData?.get(0)?.trim()}")

                            if (extraData?.size!! > 1) {
                                qrData?.i = extraData[1].trim()
                                AtekGate.console.print("EXTRA DATA [ISSUANCE] : ${extraData[1].trim()}")
                            }

                        }

                    }

                    return qrData

                }

                return null

            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }

        }
        return null
    }
}