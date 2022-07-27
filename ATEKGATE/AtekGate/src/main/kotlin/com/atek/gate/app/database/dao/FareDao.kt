package com.atek.gate.app.database.dao

import com.atek.gate.app.database.AtekGateDB
import com.atek.gate.app.database.entity.Fare
import com.atek.gate.app.event.AppEvent
import com.atek.gate.app.event.TransData
import com.atek.gate.utils.Constants
import org.greenrobot.eventbus.EventBus

object FareDao {

    suspend fun addFares(fares: List<Fare>) {

        try {

            val connection = AtekGateDB.getConnection()

            for (fare in fares) {

                val stmt = connection.prepareStatement("INSERT INTO fare (`fare_table_id`, `source`, `destination`, `fare`) VALUES(?, ?, ?, ?)")

                stmt.setInt(1, fare.fare_table_id)
                stmt.setInt(2, fare.source)
                stmt.setInt(3, fare.destination)
                stmt.setString(4, fare.fare_version)

                stmt.executeUpdate()
                stmt.clearParameters()

            }

            connection.close()

        } catch (e: Exception) {
            e.printStackTrace()
            TransData.errorType = Constants.ERROR.CONFIG_ERROR
            TransData.error = e.message ?: "Unable to insert fare !!"
            EventBus.getDefault().post(AppEvent(Constants.EVENT.ERROR))
        }

    }

}