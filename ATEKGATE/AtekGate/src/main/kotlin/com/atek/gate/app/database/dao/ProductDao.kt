package com.atek.gate.app.database.dao

import com.atek.gate.app.database.AtekGateDB
import com.atek.gate.app.database.entity.Product
import com.atek.gate.app.event.AppEvent
import com.atek.gate.app.event.TransData
import com.atek.gate.utils.Constants
import org.greenrobot.eventbus.EventBus
import java.sql.ResultSet

object ProductDao {

    suspend fun addProducts(products: List<Product>) {

        try {

            val connection = AtekGateDB.getConnection()

            for (product in products) {

                val stmt = connection.prepareStatement(
                    "INSERT INTO product ( " +
                            "pass_inventory_id, " +
                            "support_type_id, " +
                            "pass_id, " +
                            "active, " +
                            "name," +
                            "company_id, " +
                            "description, " +
                            "qr_type_id, " +
                            "same_station_over_time, " +
                            "same_station_over_time_penalty, " +
                            "same_station_over_time_max_penalty, " +
                            "other_station_over_time, " +
                            "other_station_over_time_penalty, " +
                            "other_station_over_time_max_penalty, " +
                            "over_travelling_penalty, " +
                            "entry_exit_control, " +
                            "start_validity_period, " +
                            "end_validity_period, " +
                            "balance_forward, " +
                            "reload_permit, " +
                            "validity, " +
                            "validity_unit, " +
                            "entry_validity, " +
                            "entry_validity_unit, " +
                            "grace_period, " +
                            "fare_table_id, " +
                            "fare_table_peak, " +
                            "fare_table_non_peak, " +
                            "registration_fee, " +
                            "refund_permit, " +
                            "refund_fee, " +
                            "registration_fee_refund_permit, " +
                            "registration_refund_fee, " +
                            "bundle_trips, " +
                            "daily_trip_control," +
                            "max_daily_trip, " +
                            "max_balance," +
                            "min_entry_value, " +
                            "max_exit_value, " +
                            "min_load_balance, " +
                            "min_reload_balance, " +
                            "steps_reload, " +
                            "entry_mismatch_penalty, " +
                            "exit_mismatch_penalty, " +
                            "free_exit_token_penalty, " +
                            "return_validity, " +
                            "return_validity_unit) " +
                            "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                )

                stmt.setString(1, product.pass_inventory_id)
                stmt.setString(2, product.support_type_id)
                stmt.setString(3, product.pass_id)
                stmt.setString(4, product.active)
                stmt.setString(5, product.name)
                stmt.setString(6, product.company_id)
                stmt.setString(7, product.description)
                stmt.setString(8, product.qr_type_id)
                stmt.setString(9, product.same_station_over_time)
                stmt.setString(10, product.same_station_over_time_penalty)
                stmt.setString(11, product.same_station_over_time_max_penalty)
                stmt.setString(12, product.other_station_over_time)
                stmt.setString(13, product.other_station_over_time_penalty)
                stmt.setString(14, product.other_station_over_time_max_penalty)
                stmt.setString(15, product.over_travelling_penalty)
                stmt.setString(16, product.entry_exit_control)
                stmt.setString(17, product.start_validity_period)
                stmt.setString(18, product.end_validity_period)
                stmt.setString(19, product.balance_forward)
                stmt.setString(20, product.reload_permit)
                stmt.setString(21, product.validity)
                stmt.setString(22, product.validity_unit)
                stmt.setString(23, product.entry_validity)
                stmt.setString(24, product.entry_validity_unit)
                stmt.setString(25, product.grace_period)
                stmt.setString(26, product.fare_table_id)
                stmt.setString(27, product.fare_table_peak)
                stmt.setString(28, product.fare_table_non_peak)
                stmt.setString(29, product.registration_fee)
                stmt.setString(30, product.refund_permit)
                stmt.setString(31, product.refund_fee)
                stmt.setString(32, product.registration_fee_refund_permit)
                stmt.setString(33, product.registration_refund_fee)
                stmt.setString(34, product.bundle_trips)
                stmt.setString(35, product.daily_trip_control)
                stmt.setString(36, product.max_daily_trip)
                stmt.setString(37, product.max_balance)
                stmt.setString(38, product.min_entry_value)
                stmt.setString(39, product.max_exit_value)
                stmt.setString(40, product.min_load_balance)
                stmt.setString(41, product.min_reload_balance)
                stmt.setString(42, product.steps_reload)
                stmt.setString(43, product.entry_mismatch_penalty)
                stmt.setString(44, product.exit_mismatch_penalty)
                stmt.setString(45, product.free_exit_token_penalty)
                stmt.setString(46, product.return_validity)
                stmt.setString(47, product.return_validity_unit)

                stmt.executeUpdate()
                stmt.clearParameters()

            }

            connection.close()

        } catch (e: Exception) {
            e.printStackTrace()
            TransData.errorType = Constants.ERROR.CONFIG_ERROR
            TransData.error = e.message ?: "Invalid data"
            EventBus.getDefault().post(AppEvent(Constants.EVENT.ERROR))
        }

    }

    suspend fun getProductByPassID(passID: Int): Product? {

        val connection = AtekGateDB.getConnection()

        try {

            val stmt = connection.prepareStatement("SELECT * FROM product WHERE pass_id = ? LIMIT 1")

            stmt.setInt(1, passID)
            val result: ResultSet = stmt.executeQuery()

            var product: Product? = null

            while (result.next()) {
                product = Product(
                    id = result.getInt("id"),
                    pass_inventory_id = result.getString("pass_inventory_id"),
                    support_type_id = result.getString("support_type_id"),
                    pass_id = result.getString("pass_id"),
                    active = result.getString("active"),
                    name = result.getString("name"),
                    company_id = result.getString("company_id"),
                    description = result.getString("description"),
                    qr_type_id = result.getString("qr_type_id"),
                    same_station_over_time = result.getString("same_station_over_time"),
                    same_station_over_time_penalty = result.getString("same_station_over_time_penalty"),
                    same_station_over_time_max_penalty = result.getString("same_station_over_time_max_penalty"),
                    other_station_over_time = result.getString("other_station_over_time"),
                    other_station_over_time_penalty = result.getString("other_station_over_time_penalty"),
                    other_station_over_time_max_penalty = result.getString("other_station_over_time_max_penalty"),
                    over_travelling_penalty = result.getString("over_travelling_penalty"),
                    entry_exit_control = result.getString("entry_exit_control"),
                    start_validity_period = result.getString("start_validity_period"),
                    end_validity_period = result.getString("end_validity_period"),
                    balance_forward = result.getString("balance_forward"),
                    reload_permit = result.getString("reload_permit"),
                    validity = result.getString("validity"),
                    validity_unit = result.getString("validity_unit"),
                    entry_validity = result.getString("entry_validity"),
                    entry_validity_unit = result.getString("entry_validity_unit"),
                    grace_period = result.getString("grace_period"),
                    fare_table_id = result.getString("fare_table_id"),
                    fare_table_peak = result.getString("fare_table_peak"),
                    fare_table_non_peak = result.getString("fare_table_non_peak"),
                    registration_fee = result.getString("registration_fee"),
                    refund_permit = result.getString("refund_permit"),
                    refund_fee = result.getString("refund_fee"),
                    registration_fee_refund_permit = result.getString("registration_fee_refund_permit"),
                    registration_refund_fee = result.getString("registration_refund_fee"),
                    bundle_trips = result.getString("bundle_trips"),
                    daily_trip_control = result.getString("daily_trip_control"),
                    max_daily_trip = result.getString("max_daily_trip"),
                    max_balance = result.getString("max_balance"),
                    min_entry_value = result.getString("min_entry_value"),
                    max_exit_value = result.getString("max_exit_value"),
                    min_load_balance = result.getString("min_load_balance"),
                    min_reload_balance = result.getString("min_reload_balance"),
                    steps_reload = result.getString("steps_reload"),
                    entry_mismatch_penalty = result.getString("entry_mismatch_penalty"),
                    exit_mismatch_penalty = result.getString("exit_mismatch_penalty"),
                    free_exit_token_penalty = result.getString("free_exit_token_penalty"),
                    return_validity = result.getString("return_validity"),
                    return_validity_unit = result.getString("return_validity_unit"),
                )
            }

            return product

        } catch (e: Exception) {
            e.printStackTrace()
            return null

        }
    }

}