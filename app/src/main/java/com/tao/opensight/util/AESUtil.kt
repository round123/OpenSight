package com.tao.opensight.util


import android.os.Build
import androidx.annotation.RequiresApi
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

@RequiresApi(Build.VERSION_CODES.O)
class AESUtil(base64Key: String) {
    private val aesKey: ByteArray

    init {
        if (base64Key.length == 43) {
            aesKey = Base64.getDecoder().decode("$base64Key=")
        } else {
            throw AesException(AesException.IllegalAesKey)
        }
    }

    @Throws(AesException::class)
    fun decrypt(encryptedData: String): String {
        try {
            val cipher = initCipher(Cipher.DECRYPT_MODE)
            val decodedData = Base64.getDecoder().decode(encryptedData)
            val decryptedData = cipher.doFinal(decodedData)
            return String(decryptedData, StandardCharsets.UTF_8).trim()
        } catch (e: Exception) {
            throw AesException(AesException.EncryptAESError)
        }
    }

    @Throws(AesException::class)
    fun encrypt(plainText: String): String {
        try {
            val cipher = initCipher(Cipher.ENCRYPT_MODE)
            val paddedData = pad(plainText.toByteArray(StandardCharsets.UTF_8))
            val encryptedData = cipher.doFinal(paddedData)
            return Base64.getEncoder().encodeToString(encryptedData)
        } catch (e: Exception) {
            throw AesException(AesException.EncryptAESError)
        }
    }

    @Throws(Exception::class)
    private fun initCipher(mode: Int): Cipher {
        val cipher = Cipher.getInstance("AES/CBC/NoPadding")
        cipher.init(mode, SecretKeySpec(aesKey, "AES"), IvParameterSpec(aesKey, 0, BLOCK_SIZE))
        return cipher
    }

    private fun pad(data: ByteArray): ByteArray {
        val paddingLength = BLOCK_SIZE - (data.size % BLOCK_SIZE)
        val paddingValue = (paddingLength and 0xFF).toByte()
        return Arrays.copyOf(data, data.size + paddingLength).also {
            it.fill(paddingValue, data.size, it.size)
        }
    }

    companion object {
        private const val BLOCK_SIZE = 16
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
            ValidateSignatureError -> "签名验证错误"
            ParseXmlError -> "xml解析失败"
            ComputeSignatureError -> "sha加密生成签名失败"
            IllegalAesKey -> "SymmetricKey非法"
            ValidateAppidError -> "appid校验失败"
            EncryptAESError -> "aes加密失败"
            DecryptAESError -> "aes解密失败"
            IllegalBuffer -> "解密后得到的buffer非法"
            else -> null
        }
    }
}