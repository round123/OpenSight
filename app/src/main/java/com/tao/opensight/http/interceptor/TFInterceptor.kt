package com.tao.opensight.http.interceptor

import android.os.Build
import android.util.Log
import com.tao.opensight.http.HttpsUtils.HeadUtil
import com.tao.opensight.http.HttpsUtils.HeadUtil.thefairUa
import com.tao.opensight.http.auth.AuthTools
import com.tao.opensight.http.auth.AuthTools.isAuth
import com.tao.opensight.util.Channel
import com.tao.opensight.util.VersionCode
import com.tao.opensight.util.VersionName
import com.tao.opensight.util.getScreenResolution
import com.tao.opensight.util.getUdid
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import okio.IOException
import org.json.JSONException
import org.json.JSONObject
import java.net.URLDecoder


class TFInterceptor :AInterceptor() {
    companion object {
        private const val PARAM_DEVICE_MODEL = "deviceModel"
        private const val PARAM_FIRST_CHANNEL = "first_channel"
        private const val PARAM_LAST_CHANNEL = "last_channel"
        private const val PARAM_SIZE = "size"
        private const val PARAM_SYSTEM_VERSION_CODE = "system_version_code"
        private const val PARAM_TOKEN = "token"
        private const val PARAM_UDID = "udid"
        private const val PARAM_VC = "vc"
        private const val PARAM_VN = "vn"

        private const val TAG = "TFHttpInterceptor"


        fun bodyToString(requestBody: RequestBody?): String {
            return requestBody?.let {
                val buffer = Buffer()
                try {
                    it.writeTo(buffer)
                    buffer.readUtf8()
                } catch (unused: IOException) {
                    "did not work"
                } finally {
                    buffer.close()
                }
            } ?: ""
        }
    }
    private fun iterToken(chain: Interceptor.Chain, request: Request, response: Response): Response {
        if (response.code == 400) {
            try {
                val responseBody = readResponseString(response)
                Log.d(TFHttpInterceptor.TAG, "iterToken: $responseBody")
                val jsonObject = responseBody?.let { JSONObject(it) }
                val errorCode = jsonObject?.optInt("code")

                if (errorCode in 60000..69999) {
                    if (jsonObject != null) {
                        AuthTools.handleTs(jsonObject)
                    }
                    val grantType = jsonObject?.optJSONObject("result")?.optString("grant_type", "get_token") ?: "get_token"

                    val newResponse = AuthTools.tokenSubSync(grantType)
                    Log.d(TFHttpInterceptor.TAG, "iterToken: -->tokenSubSync")
                    return if (newResponse?.code == 200 && isAuth(request)) newResponse else intercept(chain)
                }
            } catch (e: java.io.IOException) {
                e.message  // Proper error handling or logging should be placed here.
                request.url// Logging the URL can help in debugging.
            } catch (_: JSONException) {
            }
        }
        return response
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (request.url.queryParameter("isMoleia") != null) {
            val newBuilder = request.newBuilder()
            newBuilder.url(request.url.newBuilder().removeAllQueryParameters("isMoleia").build())
            return chain.proceed(newBuilder.build())
        }
        val newBuilder2 = request.newBuilder()
        newBuilder2.removeHeader("User-Agent")
        newBuilder2.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
            .addHeader("Connection", "keep-alive")
            .addHeader("x-api-key", "0530ee4341324ce2b26c23fcece80ea2")
            .addHeader("User-Agent",thefairUa)

        HeadUtil.getHeaders().forEach { (key, value) ->
            newBuilder2.addHeader(key, value.toString())
        }
        //Cookie 已经由okhttp 添加cookie jar 管理


        val newRequest = when (request.method) {
            "GET" -> handleGetRequest(request, newBuilder2)
            "POST" -> handlePostRequest(request, newBuilder2)
            else -> request
        }
        return iterToken(chain, newRequest, chain.proceed(newRequest))
    }
    private fun handleGetRequest(request: Request, builder: Request.Builder): Request {
        val urlBuilder = request.url.newBuilder().apply {
            if (!isAuth(request)) {
                addQueryParameter(PARAM_UDID, getUdid())
                addQueryParameter(PARAM_VC, VersionCode.toString())
                addQueryParameter(PARAM_VN, VersionName)
                addQueryParameter(PARAM_DEVICE_MODEL, Build.MODEL)
                addQueryParameter(PARAM_SIZE, getScreenResolution())
                addQueryParameter(PARAM_FIRST_CHANNEL, Channel)
                addQueryParameter(PARAM_LAST_CHANNEL, Channel)
                addQueryParameter(PARAM_SYSTEM_VERSION_CODE, Build.VERSION.SDK_INT.toString())
                addQueryParameter("token", "") // 如果需要token可以在这里设置
            }
        }
        Log.d("RequestLog", "Final URL: ${urlBuilder.build()}")
        return builder.url(urlBuilder.build()).build()
    }
    private fun handlePostRequest(request: Request, builder: Request.Builder): Request {
        val formBuilder = FormBody.Builder().apply {
            if (!isAuth(request)) {
                apply {
                    add(PARAM_UDID, getUdid())
                    add(PARAM_VC, VersionCode.toString())
                    add(PARAM_VN, VersionName)
                    add(PARAM_DEVICE_MODEL, Build.MODEL)
                    add(PARAM_FIRST_CHANNEL, Channel)
                    add(PARAM_SIZE, getScreenResolution())
                    add(PARAM_SYSTEM_VERSION_CODE, Build.VERSION.SDK_INT.toString())
                    add("token", "")
                }
            } else {
                request.url.queryParameter("grant_type")?.let { AuthTools.fillBuilder(this, it) }
            }

            request.body?.let { body ->
                bodyToString(body).split("&").forEach { item ->
                    item.split("=").also {
                        if (it.size > 1) add(it[0], URLDecoder.decode(it[1], "UTF-8"))
                    }
                }
            }
        }

        builder.post(formBuilder.build()) // 设置请求方法和请求体
        return builder.build() // 返回构建好的请求对象
    }
}

const val PUSH_CLIENT_ID = "push_client_id"
const val REFRESH_TOKEN = "refresh_token"
const val RESPONSE_HEADERS = "response_headers"
const val SERVER_TS_DIFF = "server_ts_diff"
const val X_THEFAIR_APPID = "X-THEFAIR-APPID"
const val X_THEFAIR_AUTH = "X-THEFAIR-AUTH"
const val X_THEFAIR_CID = "X-THEFAIR-CID"
const val X_THEFAIR_UA = "X-THEFAIR-UA"

