package com.tao.opensight.http

import android.os.Build
import com.google.gson.GsonBuilder
import com.tao.opensight.http.HttpsUtils.CustomCookieJar
import com.tao.opensight.http.HttpsUtils.SSLTrustUtil
import com.tao.opensight.util.deviceBrand
import com.tao.opensight.util.getScreenResolution
import com.tao.opensight.util.getUdid
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.SecureRandom
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory


object ApiManager {

    private const val BASE_URL = "http://baobab.kaiyanapp.com/"

    private val cookie = object : CookieJar {
        private val map = HashMap<String, MutableList<Cookie>>()

        override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
            return map[url.host] ?: ArrayList()
        }

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            map[url.host] = cookies.toMutableList()
        }
    }


    private val logging =
        HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }

    private val httpClient = OkHttpClient.Builder().connectTimeout(60000L, TimeUnit.MILLISECONDS)
        .writeTimeout(60000L, TimeUnit.MILLISECONDS)
        .readTimeout(60000L, TimeUnit.MILLISECONDS)
        .cookieJar(cookie)
        .addNetworkInterceptor(logging)
        .addInterceptor(HeaderInterceptor())
        .addInterceptor(BasicParamsInterceptor())
        .build()

    class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val request = originalRequest.newBuilder().apply {
                header("model", "Android")
                header("If-Modified-Since", "${Date()}")
                header("User-Agent", System.getProperty("http.agent") ?: "unknown")
            }.build()
            return chain.proceed(request)
        }
    }

    class BasicParamsInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url

            val url = originalHttpUrl.newBuilder().apply {
                if (!originalHttpUrl.toString().contains("api/v3/messages")) {
                    addQueryParameter("udid", getUdid())
                    //针对开眼官方【首页推荐 】api 变动， 需要单独做处理。原因：附加 vc、vn 这两个字段后，请求接口无响应。
                    if (!originalHttpUrl.toString().contains("api/v5/index/tab/allRec")) {
                        addQueryParameter("vc", "6030012")
                        addQueryParameter("vn", "6.3.1")
                    }
                    addQueryParameter("size", getScreenResolution())
                    addQueryParameter("deviceModel", Build.MODEL)
                    addQueryParameter("first_channel", deviceBrand)
                    addQueryParameter("last_channel", deviceBrand)
                    addQueryParameter("system_version_code", "${Build.VERSION.SDK_INT}")
                }
            }.build()
            val request = originalRequest.newBuilder().url(url).build()
            return chain.proceed(request)
        }
    }


    private val builder = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().registerTypeAdapterFactory(
                    GsonTypeAdapterFactory()
                ).create()
            )
        )

    private val retrofit = builder.build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create() = create(T::class.java)

    var mainPageService = create(MainPageApi::class.java)
        private set

}

fun buildTfEyeClient(): OkHttpClient {
    val hostnameVerifier = OkHttpClient.Builder()
        //.addInterceptor(ApiManager.logging)
        //.addInterceptor(TFInterceptor())
        .hostnameVerifier(SSLTrustUtil.hostnameVerifier)

    var sSLSocketFactory: SSLSocketFactory? = null
    try {
        val sSLContext = SSLContext.getInstance("TLS")
        sSLContext.init(null, arrayOf(SSLTrustUtil.trustManager), SecureRandom())
        sSLSocketFactory = sSLContext.socketFactory
    } catch (unused: Exception) {
    }

    return hostnameVerifier.sslSocketFactory(sSLSocketFactory!!, SSLTrustUtil.trustManager)
        .connectTimeout(60000L, TimeUnit.MILLISECONDS)
        .writeTimeout(60000L, TimeUnit.MILLISECONDS)
        .readTimeout(60000L, TimeUnit.MILLISECONDS)
        .cookieJar(CustomCookieJar())
        .build()
}


