package com.atek.gate.app.database.dao

import com.atek.gate.app.database.entity.Transaction
import com.atek.gate.app.event.TransData
import com.atek.gate.data.model.QrData
import com.google.gson.Gson
import com.atek.gate.app.database.AtekGateDB
import java.sql.PreparedStatement

object TransactionDao {

    suspend fun addTransaction(qrData: QrData, isSettled: Boolean = false) {

        val transaction = Transaction(
            stan = getLastTransId(),
            request = Gson().toJson(qrData),
            media_type_id = qrData.w?.toInt()!!,
            product_type_id = qrData.p?.toInt()!!,
            pass_id = qrData.p?.toInt()!!,
            ms_qr_no = qrData.m.toString(),
            sl_qr_no = qrData.t.toString(),
            trans_type = TransData.transType,
            settled = isSettled,
        )

        try {

            val connection = AtekGateDB.getConnection()

            val stmt = connection.prepareStatement("INSERT INTO gate_transactions (`stan`, `request`, `media_type_id`, `product_type_id`, `pass_id`, `ms_qr_no`, `sl_qr_no`, `trans_type`, `settled`) VALUES(?, ?, ?, ?, ?, ?, ?, ?)")

            stmt.setString(1, transaction.stan)
            stmt.setString(2, transaction.request)
            stmt.setInt(3, transaction.media_type_id)
            stmt.setInt(4, transaction.product_type_id)
            stmt.setInt(5, transaction.pass_id)
            stmt.setString(6, transaction.ms_qr_no)
            stmt.setString(7, transaction.sl_qr_no)
            stmt.setInt(8, transaction.trans_type)
            stmt.setBoolean(9, transaction.settled)

            stmt.executeUpdate()
            stmt.clearParameters()

            connection.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    suspend fun checkIfTransAlreadyExit(slaveID: String, transType: Int): Boolean {

        try {

            val connection = AtekGateDB.getConnection()
            val query = "SELECT * FROM gate_transactions WHERE sl_qr_no = ? AND trans_type = ? LIMIT 1"

            val pstmt: PreparedStatement = connection.prepareStatement(query)

            pstmt.setString(1, slaveID)
            pstmt.setInt(2, transType)

            val result = pstmt.executeQuery()

            var transaction: Transaction? = null

            while (result.next()) {
                transaction = Transaction(
                    id = result.getInt("id"),
                    stan = result.getString("stan"),
                    request = result.getString("request"),
                    media_type_id = result.getInt("media_type_id"),
                    product_type_id = result.getInt("product_type_id"),
                    pass_id = result.getInt("pass_id"),
                    ms_qr_no = result.getString("ms_qr_no"),
                    sl_qr_no = result.getString("sl_qr_no"),
                    trans_type = result.getInt("trans_type"),
                    settled = result.getBoolean("settled"),
                )
            }

            return transaction != null

        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    suspend fun getAllUnSyncedTrans(): MutableList<Transaction>? {

        try {

            val connection = AtekGateDB.getConnection()

            val stmt = connection.prepareStatement("SELECT * FROM gate_transactions WHERE settled = ?")
            stmt.setBoolean(1, false)
            val result = stmt.executeQuery()

            val transaction = mutableListOf<Transaction>()

            while (result.next()) {
                transaction.add(
                    Transaction(
                        id = result.getInt("id"),
                        stan = result.getString("stan"),
                        request = result.getString("request"),
                        media_type_id = result.getInt("media_type_id"),
                        product_type_id = result.getInt("product_type_id"),
                        pass_id = result.getInt("pass_id"),
                        ms_qr_no = result.getString("ms_qr_no"),
                        sl_qr_no = result.getString("sl_qr_no"),
                        trans_type = result.getInt("trans_type"),
                        settled = result.getBoolean("settled"),
                    )
                )
            }

            connection.close()
            return transaction

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    suspend fun updateTransaction(id: Int): Boolean {
        return true
    }

    private suspend fun getLastTransId(): String {

        try {

            val connection = AtekGateDB.getConnection()

            val stmt = connection.prepareStatement("SELECT id FROM gate_transactions ORDER BY DESC id LIMIT 1")
            val result = stmt.executeQuery()

            var id: Int? = null

            while (result.next()) {
                id = result.getInt("id")
            }

            connection.close()

            return id.toString().padEnd(6, '0')

        } catch (e: Exception) {

            e.printStackTrace()
            return "000000"

        }

    }


}

