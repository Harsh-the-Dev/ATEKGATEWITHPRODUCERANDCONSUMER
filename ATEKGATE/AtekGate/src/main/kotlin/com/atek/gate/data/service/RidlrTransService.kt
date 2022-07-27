package com.atek.gate.data.service

import com.atek.gate.app.AtekGate
import com.atek.gate.app.database.dao.ProductDao
import com.atek.gate.app.database.dao.TransactionDao
import com.atek.gate.data.model.ridlr.request.Data
import com.atek.gate.data.model.ridlr.request.ScanEventRequest
import com.atek.gate.data.repository.ConfigRepository
import com.atek.gate.utils.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class RidlrTransService {

    fun startTranscationService() {

        CoroutineScope(Dispatchers.IO).launch {

            while (isActive) {

                val transactions = TransactionDao.getAllUnSyncedTrans()

                if (transactions != null) {

                    for (transaction in transactions) {

                        val product = ProductDao.getProductByPassID(transaction.pass_id)

                        val scanEventRequest = ScanEventRequest(
                            Data(
                                transactionId = transaction.sl_qr_no,
                                master_txn_id = transaction.ms_qr_no,
                                equipment_id = AtekGate.equipment.equipment_id,
                                support_type = transaction.product_type_id.toString(),
                                qr_type = product?.qr_type_id!!,
                                validation_type = if(transaction.trans_type == 101) "ENTRY" else "EXIT",
                                validation_station_id = AtekGate.equipment.station_id.toString(),
                                scan_time = System.currentTimeMillis().toString(),
                                pass_id = transaction.pass_id.toString(),
                            )
                        )

                        when (val response = ConfigRepository.sendScanEvent(scanEventRequest)) {
                            is Response.Success -> {
                                AtekGate.console.print("TRANSACTION UPDATED: ${response.data}")
                                TransactionDao.updateTransaction(transaction.id!!)
                            }
                            is Response.Error -> {
                                AtekGate.console.print("FAILED TO UPLOAD TRANSACTION -> ERROR: ${response.data}")
                            }
                        }
                    }
                }
            }
        }

    }
}