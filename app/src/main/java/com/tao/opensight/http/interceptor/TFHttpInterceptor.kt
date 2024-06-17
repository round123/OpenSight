package com.tao.opensight.http.interceptor

import MMKVUtil
import android.os.Build
import android.util.Log
import com.tao.opensight.http.auth.AuthTools
import com.tao.opensight.http.HttpsUtils.HeadUtil
import com.tao.opensight.http.HttpsUtils.HeadUtil.wdj_auth
import com.tao.opensight.util.deviceBrand
import com.tao.opensight.util.getScreenResolution
import com.tao.opensight.util.getUdid
import okhttp3.*
import okio.Buffer
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.URLDecoder

/**
 * sign校验 没搞定，不知道差了哪一步，所以还是用旧Api咯
 */
class TFHttpInterceptor() :AInterceptor(){
    companion object {
        const val TAG = "TFHttpInterceptor"
        const val X_THEFAIR_APPID = "X-THEFAIR-APPID"
        const val X_THEFAIR_AUTH = "X-THEFAIR-AUTH"
        const val X_THEFAIR_CID = "X-THEFAIR-CID"
        const val X_THEFAIR_UA = "X-THEFAIR-UA"
        fun bodyToString(requestBody: RequestBody?): String {
            val buffer = Buffer()
            return try {
                requestBody?.writeTo(buffer)
                buffer.readUtf8()
            } catch (e: IOException) {
                "did not work"
            } finally {
                buffer.close()
            }
        }
    }

    private fun iterToken(chain: Interceptor.Chain, request: Request, response: Response): Response {

        if (response.code == 400) {
            try {
                val responseBody = readResponseString(response)
                Log.d(TAG, "iterToken: $responseBody")
                val jsonObject = responseBody?.let { JSONObject(it) }
                val errorCode = jsonObject?.optInt("code")

                if (errorCode in 60000..69999) {
                    if (jsonObject != null) {
                        AuthTools.handleTs(jsonObject)
                    }
                    val grantType = jsonObject?.optJSONObject("result")?.optString("grant_type", "get_token") ?: "get_token"

                    val newResponse = AuthTools.tokenSubSync(grantType)
                    Log.d(TAG, "iterToken: -->tokenSubSync")
                    return if (newResponse?.code == 200 && AuthTools.isAuth(request)) newResponse else intercept(chain)
                }
            } catch (e: IOException) {
                e.message  // Proper error handling or logging should be placed here.
                request.url// Logging the URL can help in debugging.
            } catch (e: JSONException) {
                // Handle JSON parsing errors if necessary
            }
        }
        return response
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        if (request.url.queryParameter("isMoleia") != null) {
            builder.url(request.url.newBuilder().removeAllQueryParameters("isMoleia").build())
        }

        builder.removeHeader("User-Agent")
            .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
            .addHeader("Connection", "keep-alive")
            .addHeader("x-api-key", "0530ee4341324ce2b26c23fcece80ea2")
            .addHeader("User-Agent", HeadUtil.userAgent)

        HeadUtil.getHeaders().forEach { (key, value) ->
            builder.addHeader(key, value.toString())
        }

        var cookieHeader = "ky_udid=${getUdid()}"

        if (HeadUtil.randomCookie() == true) {
            cookieHeader += if ((System.currentTimeMillis() % 2L).toInt() == 0) {
                ";ky_auth=sdfsfsf"
            } else {
                ";ky_auth=${wdj_auth}"
            }
        } else {
            cookieHeader += ";ky_auth=${MMKVUtil.decode("WDJ_AUTH","")}"
        }

//        HeadUtil.responseCookies.forEach { cookie ->
//            val cookieStr = "${cookie.name}=${cookie.value}"
//            if (!cookieHeader.contains(cookieStr)) {
//                cookieHeader = "$cookieHeader;$cookieStr"
//            }
//        }

        builder.addHeader("Cookie", cookieHeader)
        val newRequest = when (request.method) {
            "GET" -> handleGetRequest(request, builder)
            "POST" -> handlePostRequest(request, builder)
            else -> request
        }

        val response = iterToken(chain, newRequest, chain.proceed(newRequest))
        return response
    }

    private fun handleGetRequest(request: Request, builder: Request.Builder): Request {
        val urlBuilder = request.url.newBuilder().apply {
            if (!AuthTools.isAuth(request)) {
                addQueryParameter("udid", getUdid()).also { Log.d("RequestLog", "Added udid: ${getUdid()}") }
                addQueryParameter("vc", "7070010").also { Log.d("RequestLog", "Added vc: 7070010") }
                addQueryParameter("vn", "7.7.10").also { Log.d("RequestLog", "Added vn: 7.7.10") }
                addQueryParameter("deviceModel", Build.MODEL).also { Log.d("RequestLog", "Added deviceModel: ${Build.MODEL}") }
                addQueryParameter("size", getScreenResolution()).also { Log.d("RequestLog", "Added size: ${getScreenResolution()}") }
                addQueryParameter("first_channel", deviceBrand).also { Log.d("RequestLog", "Added first_channel: $deviceBrand") }
                addQueryParameter("last_channel", deviceBrand).also { Log.d("RequestLog", "Added last_channel: $deviceBrand") }
                addQueryParameter("system_version_code", Build.VERSION.SDK_INT.toString()).also { Log.d("RequestLog", "Added system_version_code: ${Build.VERSION.SDK_INT}") }
                addQueryParameter("token", "").also { Log.d("RequestLog", "Added token: ") }
            }
        }
        Log.d("RequestLog", "Final URL: ${urlBuilder.build()}")
        return builder.url(urlBuilder.build()).build()
    }

    private fun handlePostRequest(request: Request, builder: Request.Builder): Request {
        val formBodyBuilder = FormBody.Builder().apply {
            if (!AuthTools.isAuth(request)) {
                add("udid", getUdid()).also { Log.d("PostRequestLog", "Added udid: ${getUdid()}") }
                add("vc", "7070010").also { Log.d("PostRequestLog", "Added vc: 7070010") }
                add("vn", "7.7.10").also { Log.d("PostRequestLog", "Added vn: 7.7.10") }
                add("deviceModel", Build.MODEL).also { Log.d("PostRequestLog", "Added deviceModel: ${Build.MODEL}") }
                add("first_channel", deviceBrand).also { Log.d("PostRequestLog", "Added first_channel: $deviceBrand") }
                add("size", getScreenResolution()).also { Log.d("PostRequestLog", "Added size: ${getScreenResolution()}") }
                add("system_version_code", Build.VERSION.SDK_INT.toString()).also { Log.d("PostRequestLog", "Added system_version_code: ${Build.VERSION.SDK_INT}") }
                add("token", "").also { Log.d("PostRequestLog", "Added token: ") }
            } else {
                request.url.queryParameter("grant_type")?.let {
                    AuthTools.fillBuilder(this, it)
                    Log.d("PostRequestLog", "Handled grant_type with AuthTools: $it")
                }
            }

            // 日志记录复制已有 body 参数的添加情况
            bodyToString(request.body).split("&").forEach {
                val parts = it.split("=")
                if (parts.size > 1) {
                    add(parts[0], URLDecoder.decode(parts[1], "UTF-8"))
                    Log.d("PostRequestLog", "Added existing body param ${parts[0]} with value ${URLDecoder.decode(parts[1], "UTF-8")}")
                }
            }
        }
        Log.d("PostRequestLog", "Final POST body built and request being sent.")
        return builder.post(formBodyBuilder.build()).build()
    }


}