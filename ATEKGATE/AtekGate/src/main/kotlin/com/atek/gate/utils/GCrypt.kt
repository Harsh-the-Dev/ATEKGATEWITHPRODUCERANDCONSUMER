package com.atek.gate.utils

import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.Cipher
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object GCrypt {

    private lateinit var ivspec: IvParameterSpec
    private lateinit var keyspec: SecretKeySpec
    private lateinit var ivTpspec: IvParameterSpec
    private lateinit var keyTpspec: SecretKeySpec
    private lateinit var cipher: Cipher

    init {

        try {

            val _ivspec = "fedcba9876543210"
            val _keyspec = "0123456789abcdef"
            val _ivTpspec = "fedcba9876543210"
            val _keyTpspec = "0123456789abcdef"

            ivspec = IvParameterSpec(_ivspec.toByteArray())
            keyspec = SecretKeySpec(_keyspec.toByteArray(), "AES")

            ivTpspec = IvParameterSpec(_ivTpspec.toByteArray())
            keyTpspec = SecretKeySpec(_keyTpspec.toByteArray(), "AES")

            cipher = Cipher.getInstance("AES/CBC/NoPadding")

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e2: NoSuchPaddingException) {
            e2.printStackTrace()
        }
    }

    fun decrypt(code: String, qrType: Int): ByteArray? {

        when (qrType) {
            1-> {
                cipher.init(2, keyspec, ivspec)
            }
            else -> {
                cipher.init(2, keyTpspec, ivTpspec)
            }
        }

        val decodedString = try {
            cipher.doFinal(hexToBytes(code))
        } catch (e: Exception) {
            cipher.doFinal(Base64.getMimeDecoder().decode(code))
        }

        println(String(decodedString))
        return decodedString

    }

    fun hexToBytes(str: String?): ByteArray? {
        if (str == null) {
            return null
        }
        if (str.length < 2) {
            return null
        }
        val len = str.length / 2
        val buffer = ByteArray(len)
        for (i in 0 until len) {
            buffer[i] = str.substring(i * 2, i * 2 + 2).toInt(16).toByte()
        }
        return buffer
    }

}
