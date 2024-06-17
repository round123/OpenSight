package com.tao.opensight.http.HttpsUtils

import android.annotation.SuppressLint
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession
import javax.net.ssl.X509TrustManager

/**
 * 转换自 Java 类 `HttpsUtils.java`。
 * 从文件 `D:\开眼app\开眼_7.7.10\classes3.dex` 加载。
 * 主要处理 SSL/TLS 连接的信任管理和主机名验证。
 */
object SSLTrustUtil {

    val trustManager: X509TrustManager = @SuppressLint("CustomX509TrustManager")
    object : X509TrustManager {
        @SuppressLint("TrustAllX509TrustManager")
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            // 不做任何操作
        }

        @SuppressLint("TrustAllX509TrustManager")
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            // 不做任何操作
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    }

    val hostnameVerifier: HostnameVerifier = HostnameVerifier { hostname, session -> true }


}