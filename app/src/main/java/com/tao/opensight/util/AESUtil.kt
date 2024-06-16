package com.tao.opensight.util


import android.util.Base64
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AESUtil(base64Key: String) {
    private val aesKey: ByteArray

    init {
        if (base64Key.length == 43) {
            aesKey = Base64.decode(base64Key + "=", Base64.DEFAULT)
        } else {
            throw AesException(-40004)
        }
    }

    @Throws(AesException::class)
    fun decrypt(base64Encrypted: String): String {
        return try {
            val cipher = Cipher.getInstance("AES/CBC/NoPadding")
            val keySpec = SecretKeySpec(aesKey, "AES")
            val ivSpec = IvParameterSpec(aesKey.copyOfRange(0, 16))
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
            val decryptedBytes = cipher.doFinal(Base64.decode(base64Encrypted, Base64.DEFAULT))
            String(decryptedBytes, StandardCharsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
            throw AesException(-40006)
        }
    }

    @Throws(AesException::class)
    fun encrypt(plainText: String): String {
        return try {
            var inputBytes = plainText.toByteArray(StandardCharsets.UTF_8)
            val blockSize = Cipher.getInstance("AES/CBC/NoPadding").blockSize
            val paddingLength = blockSize - (inputBytes.size % blockSize)
            inputBytes = inputBytes.copyOf(inputBytes.size + paddingLength)
            inputBytes.fill(paddingLength.toByte(), inputBytes.size - paddingLength, inputBytes.size)

            val cipher = Cipher.getInstance("AES/CBC/NoPadding")
            val keySpec = SecretKeySpec(aesKey, "AES")
            val ivSpec = IvParameterSpec(aesKey, 0, 16)
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
            val encryptedBytes = cipher.doFinal(inputBytes)
            Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            throw AesException(-40006)
        }
    }
}
class AesException(val code: Int) : Exception(getMessage(code)) {
    companion object {
        const val ComputeSignatureError = -40003
        const val DecryptAESError = -40007
        const val EncryptAESError = -40006
        const val IllegalAesKey = -40004
        const val IllegalBuffer = -40008
        const val OK = 0
        const val ParseXmlError = -40002
        const val ValidateAppidError = -40005
        const val ValidateSignatureError = -40001

        private fun getMessage(code: Int): String? = when (code) {
            ValidateSignatureError -> "Signature validation error."
            ParseXmlError -> "Error parsing XML."
            ComputeSignatureError -> "Error computing signature."
            IllegalAesKey -> "Invalid AES key length."
            ValidateAppidError -> "AppID validation error."
            EncryptAESError -> "AES encryption error."
            DecryptAESError -> "AES decryption error."
            IllegalBuffer -> "Invalid buffer."
            else -> null
        }
    }
}