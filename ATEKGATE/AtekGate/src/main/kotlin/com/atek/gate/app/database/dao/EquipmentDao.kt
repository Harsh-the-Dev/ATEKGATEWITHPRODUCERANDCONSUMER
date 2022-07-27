package com.atek.gate.app.database.dao

import com.atek.gate.app.database.entity.Equipment
import com.atek.gate.app.event.AppEvent
import com.atek.gate.utils.Constants
import com.atek.gate.app.database.AtekGateDB
import com.atek.gate.app.event.TransData
import org.greenrobot.eventbus.EventBus

object EquipmentDao {

    suspend fun addEquipment(equipment: Equipment) {

        try {

            val connection = AtekGateDB.getConnection()
            val stmt = connection.prepareStatement("INSERT INTO equipment (`equipment_id`, `eq_type_id`, `eq_direction_id`, `status`, `station_id`, `end_date`) VALUES(?, ?, ?, ?, ?, ?)")

            stmt.setString(1, equipment.equipment_id)
            stmt.setInt(2, equipment.eq_type_id)
            stmt.setString(3, equipment.gateDirection)
            stmt.setString(4, equipment.status)
            stmt.setInt(5, equipment.station_id)
            stmt.setString(6, equipment.end_date)

            stmt.executeUpdate()
            stmt.clearParameters()
            connection.close()

        } catch (e: Exception) {
            e.printStackTrace()
            TransData.errorType = Constants.ERROR.CONFIG_ERROR
            TransData.error = e.message ?: "Unable to insert equipment !!"
            EventBus.getDefault().post(AppEvent(Constants.EVENT.ERROR))
        }

    }

    suspend fun getEquipment(): Equipment? {

        try {

            val connection = AtekGateDB.getConnection()
            val stmt = connection.createStatement()
            val result = stmt.executeQuery("SELECT * FROM equipment LIMIT 1")

            var equipment: Equipment? = null

            while (result.next()) {
                equipment = Equipment(
                    equipment_id = result.getString("equipment_id"),
                    eq_type_id = result.getInt("eq_type_id"),
                    gateDirection = result.getString("eq_direction_id"),
                    end_date = result.getString("end_date"),
                    status = result.getString("status"),
                    station_id = result.getInt("station_id"),
                )
            }

            connection.close()
            return equipment

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

}