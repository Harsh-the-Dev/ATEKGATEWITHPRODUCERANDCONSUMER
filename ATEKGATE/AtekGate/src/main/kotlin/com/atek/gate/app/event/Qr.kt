package com.atek.gate.app.event

//import androidx.compose.runtime.internal.isLiveLiteralsEnabled
import Kafka.dataConsumer
import com.atek.gate.data.model.QrData
import com.atek.gate.utils.GCrypt
import com.google.gson.GsonBuilder

object manager {
    fun runner() {
        while (true) {

            print("Enter QR Data: ")
            val encodedData = readLine()
            if (encodedData != null) {
                val qrData = decodeHandler(encodedData)
                dataConsumer = qrData
                println(qrData)
                break
            } else {
                println("Invalid data ?")
            }
        }
    }

    fun decodeHandler(encodedData: String?): String? {

        val gson = GsonBuilder()
            .setLenient()
            .create()
        lateinit var qrData: QrData
        if (encodedData != null) {
            println("success")
            val encodedDataArray = encodedData.trim().split('~')
            val decodedDataArray = arrayOfNulls<String>(2)
            try {
                println("now")
                val temDecodedData = GCrypt.decrypt(
                    encodedDataArray[0],
                    1
                )
                val x: String
                if (temDecodedData != null) {
                    println("here")
                    decodedDataArray[0] = String(temDecodedData).trim()
                    println("destroying")
                    println(decodedDataArray[0])
                    println("falling")


                    val qrData = decodedDataArray[0]!!


//                var data= gson.fromJson(qrData,QrData::class.java)
//println(data.d)
//                println(data)
                    println("down")
                    if (encodedDataArray.size > 1) {

                        var encodedDateTime = encodedDataArray[encodedDataArray.size - 1]

                        if (encodedDateTime.trim() == "") {
                            encodedDateTime = encodedDataArray[encodedDataArray.size - 2]
                        }

                        val temDecodedDateTimeData = GCrypt.decrypt(
                            encodedDateTime,
                            2
                        )

                        if (temDecodedDateTimeData != null) {

                            decodedDataArray[1] = String(temDecodedDateTimeData)

                            val extraData = decodedDataArray[1]?.split("\\|".toRegex())
                            if (extraData != null) {

                                val ec = extraData.get(0).trim()
                                println(ec)

                            }


//                        qrdata?.ec = extraData?.get(0)?.trim()

                            if (extraData?.size!! > 1) {
                                val i = extraData[1].trim()
                                println(i)
//                            qrData?.i = extraData[1].trim()
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
/*fun decryptQRToken(qrString: String): QrData? {
    val qrEncryptedData = qrString.split("~".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    println(qrEncryptedData.contentToString())
    val decryptedData = arrayOfNulls<String>(2)
    val mCrypt = MCrypt()
    return try {
        decryptedData[0] = String(mCrypt.decrypt(qrEncryptedData[0], 1)!!)
        println(decryptedData[0])
        *//*val tokenData = Gson().fromJson(decryptedData[0], QrData::class.java)*//*
        if (qrEncryptedData.size > 1) {
            var encryptedDateTime = qrEncryptedData[qrEncryptedData.size - 1]
            if (encryptedDateTime.trim { it <= ' ' } == "") {
                encryptedDateTime = qrEncryptedData[qrEncryptedData.size - 2]
            }
            decryptedData[1] = String(mCrypt.decrypt(encryptedDateTime, 2)!!)
            println(decryptedData[1])
            val extraData = decryptedData[1]!!.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        }
        null
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}*/
