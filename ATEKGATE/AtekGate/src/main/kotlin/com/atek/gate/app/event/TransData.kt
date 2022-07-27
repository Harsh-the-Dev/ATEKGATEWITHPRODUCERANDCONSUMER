package com.atek.gate.app.event

import com.atek.gate.data.model.QrData
import com.atek.gate.utils.Constants

object TransData {

    // Scanned qr data
    lateinit var qrData: QrData

    // Transaction type
    var transType: Int = Constants.ENTRY_TRANSACTION

    // Transaction gate
    lateinit var transGate: Constants.GATE

    // Transaction scanner
    lateinit var transScanner: Constants.SCANNER

    // Error type
    lateinit var errorType: Constants.ERROR

    // Error
    lateinit var error: String

}