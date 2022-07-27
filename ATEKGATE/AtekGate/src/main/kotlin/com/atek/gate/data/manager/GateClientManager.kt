package com.atek.gate.data.manager

import com.atek.gate.app.event.SocketEvent
import com.atek.gate.data.model.event.SocketChat
import com.atek.gate.utils.Constants
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import java.io.*
import java.net.Socket

class GateClientManager {

    private lateinit var socket: Socket
    private lateinit var bufferedReader: BufferedReader
    private lateinit var bufferedWriter: BufferedWriter

    init {
        try {
            socket = Socket("10.13.3.202", 2222)
            bufferedWriter = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
            bufferedReader = BufferedReader(InputStreamReader(socket.getInputStream()))
        } catch (e: IOException) {
            e.printStackTrace()
            close()
        }
    }

    fun sendMessage(message: String) {
        try {
            bufferedWriter.write(message)
            bufferedWriter.newLine()
            bufferedWriter.flush()
        } catch (e: IOException) {
            close()
            e.printStackTrace()
            e.printStackTrace()
        }
    }

    fun listenForMessage() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                while (socket.isConnected) {
                    var message = try {
                     bufferedReader.readLine()
                    } catch (e: IOException) {
                        e.printStackTrace()
                        null
                    }
                    if (message != null) {
                        println(message)
                        EventBus.getDefault().post(
                            SocketEvent(
                                Constants.SOCKET.MESSAGE_RECEIVED,
                                Gson().fromJson(bufferedReader.readLine(), SocketChat::class.java)
                            )
                        )
                    }
                          }
  }
        }
    }

    private fun close() {
        try {
            bufferedReader.close()
            bufferedWriter.close()
            socket.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}