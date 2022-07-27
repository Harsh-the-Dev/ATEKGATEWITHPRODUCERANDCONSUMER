package com.atek.gate.app.database

import java.sql.Connection
import java.sql.DriverManager


object AtekGateDB {

    fun getConnection(): Connection {
        return DriverManager.getConnection("jdbc:sqlite:AtekGateGB.db")
    }

    suspend fun createTables() {

        val CREATE_FARES_TABLE = "CREATE TABLE IF NOT EXISTS `fare` (" +
                "  `id` INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  `fare_table_id` INTEGER," +
                "  `source` TEXT," +
                "  `destination` TEXT," +
                "  `fare` REAL" +
                ")";

        val CREATE_EQUIPMENT_TABLE = "CREATE TABLE IF NOT EXISTS `equipment` (" +
                "  `id` INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  `equipment_id` TEXT," +
                "  `eq_type_id` INTEGER," +
                "  `eq_direction_id` TEXT," +
                "  `status` TEXT," +
                "  `station_id` TEXT," +
                "  `end_date` TEXT" +
                ")";

        val CREATE_PRODUCTS_TABLE = "CREATE TABLE IF NOT EXISTS `product` (" +
                "  `id` INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  `pass_inventory_id` TEXT," +
                "  `support_type_id` TEXT," +
                "  `pass_id` TEXT," +
                "  `active` TEXT," +
                "  `name` TEXT," +
                "  `company_id` TEXT," +
                "  `description` TEXT," +
                "  `qr_type_id` TEXT," +
                "  `same_station_over_time` TEXT," +
                "  `same_station_over_time_penalty` TEXT," +
                "  `same_station_over_time_max_penalty` TEXT," +
                "  `other_station_over_time` TEXT," +
                "  `other_station_over_time_penalty` TEXT," +
                "  `other_station_over_time_max_penalty` TEXT," +
                "  `over_travelling_penalty` TEXT," +
                "  `entry_exit_control` TEXT," +
                "  `start_validity_period` TEXT," +
                "  `end_validity_period` TEXT," +
                "  `balance_forward` TEXT," +
                "  `reload_permit` TEXT," +
                "  `validity` TEXT," +
                "  `validity_unit` TEXT," +
                "  `entry_validity` TEXT," +
                "  `entry_validity_unit` TEXT," +
                "  `grace_period` TEXT," +
                "  `fare_table_id` TEXT," +
                "  `fare_table_peak` TEXT," +
                "  `fare_table_non_peak` TEXT," +
                "  `registration_fee` TEXT," +
                "  `refund_permit` TEXT," +
                "  `refund_fee` TEXT," +
                "  `registration_fee_refund_permit` TEXT," +
                "  `registration_refund_fee` TEXT," +
                "  `bundle_trips` TEXT," +
                "  `daily_trip_control` TEXT," +
                "  `max_daily_trip` TEXT," +
                "  `max_balance` TEXT," +
                "  `min_entry_value` TEXT," +
                "  `max_exit_value` TEXT," +
                "  `min_load_balance` TEXT," +
                "  `min_reload_balance` TEXT," +
                "  `steps_reload` TEXT," +
                "  `cchs_reference` TEXT," +
                "  `entry_mismatch_penalty` TEXT," +
                "  `exit_mismatch_penalty` TEXT," +
                "  `free_exit_token_penalty` TEXT," +
                "  `return_validity` TEXT," +
                "  `return_validity_unit` TEXT" +
                ")";

        val CREATE_TRANSACTIONS_TABLE = "CREATE TABLE IF NOT EXISTS `gate_transactions` (" +
                "  `id` INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  `stan` TEXT," +
                "  `request` TEXT," +
                "  `media_type_id` TEXT," +
                "  `product_type_id` TEXT," +
                "  `ms_qr_no` TEXT," +
                "  `sl_qr_no` TEXT," +
                "  `trans_type` TEXT," +
                "  `settled` TEXT," +
                "  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ")";

        try {
            val connection = getConnection()
            val stmt = connection.createStatement()

            stmt.execute("DROP TABLE IF EXISTS `fare`");
            stmt.execute("DROP TABLE IF EXISTS `gate_transactions`");
            stmt.execute("DROP TABLE IF EXISTS `equipment`");
            stmt.execute("DROP TABLE IF EXISTS `product`");

            stmt.execute(CREATE_FARES_TABLE)
            stmt.execute(CREATE_EQUIPMENT_TABLE)
            stmt.execute(CREATE_TRANSACTIONS_TABLE)
            stmt.execute(CREATE_PRODUCTS_TABLE)

            connection.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}