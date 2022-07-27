package com.atek.gate.utils

object Constants {

    const val ENTRY_TRANSACTION = 101
    const val EXIT_TRANSACTION = 102

    // Ports
    const val ttyUSB0 = "/dev/ttyS0"
    const val ttyAMC0 = "/dev/ttyACM0"
    const val ttyAMC1 = "/dev/ttyACM1"

    // Server
//    const val ServerIP = "10.13.3.202"
//    const val ServerPort = "10045"

    // Commands
    val PULSE = byteArrayOf(0xaa.toByte(), 0xc0.toByte(), 0x00.toByte(), 0x00.toByte(), 0xc0.toByte())
    val BI_DI = byteArrayOf(0xaa.toByte(), 0x33.toByte(), 0x00.toByte(), 0x00.toByte(), 0x33.toByte())
    val ENTRY_ONLY = byteArrayOf(0xaa.toByte(), 0x31.toByte(), 0x00.toByte(), 0x00.toByte(), 0x31.toByte())
    val EXIT_ONLY = byteArrayOf(0xaa.toByte(), 0x32.toByte(), 0x00.toByte(), 0x00.toByte(), 0x32.toByte())
    val ENTRY_GATE_OPEN_CMD = byteArrayOf(
        0xaa.toByte(),
        0x10.toByte(),
        0x00.toByte(),
        0x08.toByte(),
        0x01.toByte(),
        0x00.toByte(),
        0x00.toByte(),
        0x00.toByte(),
        0x00.toByte(),
        0x00.toByte(),
        0x00.toByte(),
        0x00.toByte(),
        0x19.toByte()
    )
    val EXIT_GATE_OPEN_CMD = byteArrayOf(
        0xaa.toByte(),
        0x20.toByte(),
        0x00.toByte(),
        0x08.toByte(),
        0x01.toByte(),
        0x00.toByte(),
        0x00.toByte(),
        0x00.toByte(),
        0x00.toByte(),
        0x00.toByte(),
        0x00.toByte(),
        0x00.toByte(),
        0x29.toByte()
    )

    // GATE Response
    val ENTRY_GATE_RESPONSE = "AA,01,00,01,10,10"
    val EXIT_GATE_RESPONSE = "AA,01,00,01,20,20"

    // States
    enum class SCREENS {
        CONFIG, UPDATE, MAIN, ENTRY, EXIT, ERROR
    }

    // Operations
    enum class ERROR {
        CONFIG_ERROR, UPDATE_ERROR, INVALID_QR
    }

    // Event
    enum class EVENT {
        CONFIG_SUCCESS, QR_SCANNED, GATE_OPENED, ERROR
    }

    // GATE MODES
    enum class MODE {
        ENTRY_ONLY,
        EXIT_ONLY,
        BOTH
    }

    enum class GATE {
        ENTRY_GATE,
        EXIT_GATE
    }

    enum class SCANNER {
        SCANNED_AT_ENTRY,
        SCANNED_AT_EXIT
    }

    enum class TRANSACTION_TYPE {
        QR_DATA,
        EXTRA_DATA,
    }

    enum class SOCKET {
        MESSAGE_RECEIVED,
    }

}