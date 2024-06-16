package com.tao.opensight.http.interceptor


import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okio.Buffer;


abstract class AInterceptor : Interceptor {

    companion object {
        @Throws(EOFException::class)
        fun isPlaintext(buffer: Buffer): Boolean {
            try {
                val bufferSize = if (buffer.size < 64) buffer.size else 64
                val previewBuffer = Buffer()
                buffer.copyTo(previewBuffer, 0, bufferSize)
                for (i in 0 until 16) {
                    if (previewBuffer.exhausted()) break
                    val codePoint = previewBuffer.readUtf8CodePoint()
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                        return false
                    }
                }
                return true
            } catch (e: EOFException) {
                return false
            }
        }
    }

    protected fun bodyEncoded(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"]
        return contentEncoding != null && !contentEncoding.equals("identity", ignoreCase = true)
    }

    @Throws(IOException::class)
    protected fun readResponseString(response: Response): String? {
        val responseBody = response.body!!
        if (!bodyEncoded(response.headers)) {
            val source = responseBody.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer
            var charset: Charset = StandardCharsets.UTF_8
            val contentType: MediaType? = responseBody.contentType()
            contentType?.let {
                try {
                    charset = it.charset(StandardCharsets.UTF_8) ?: charset
                } catch (e: IllegalArgumentException) {
                    return null // Unsupported charset
                }
            }
            if (!isPlaintext(buffer)) return null
            if (responseBody.contentLength() != 0L) {
                return buffer.clone().readString(charset)
            }
        }
        return null
    }
}