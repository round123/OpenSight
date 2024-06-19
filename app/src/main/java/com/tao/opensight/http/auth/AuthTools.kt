//package com.tao.opensight.http.auth
//
//
//import AESUtil
//import AesException
//import android.os.Build
//import androidx.annotation.RequiresApi
//import com.blankj.utilcode.util.DeviceUtils.getAndroidID
//import com.tao.opensight.ext.nullToEmpty
//import com.tao.opensight.http.ApiManager
//import com.tao.opensight.http.HttpsUtils.HeadUtil.getTs
//import com.tao.opensight.http.HttpsUtils.HeadUtil.refreshToken
//import com.tao.opensight.http.HttpsUtils.HeadUtil.thefairAuth
//import com.tao.opensight.http.HttpsUtils.HeadUtil.thefairCid
//import com.tao.opensight.http.HttpsUtils.HeadUtil.thefair_device_id
//import com.tao.opensight.http.HttpsUtils.HeadUtil.tsDiff
//import com.tao.opensight.util.getImei
//import com.tao.opensight.util.getImsi
//import com.tao.opensight.util.getScreenResolution
//import com.tao.opensight.util.getUdid
//import okhttp3.FormBody
//import okhttp3.Request
//import okhttp3.Response
//import org.json.JSONException
//import org.json.JSONObject
//import java.io.IOException
//
//
//object AuthTools {
//    var mydeviceInfo: String? = null
//
//
//    fun fillBuilder(
//        paramBuilder: FormBody.Builder,
//        paramString: String,
//    ): FormBody.Builder {
//        val ts = getTs().toString()
//        val sign = getSign(getSignString(paramString, ts))
//        if ("refresh_token" == paramString) {
//            paramBuilder.add("refresh_token", refreshToken.nullToEmpty())
//        }
//        return paramBuilder.add("grant_type", paramString)
//            .add("sign", sign)
//            .add("ts", ts)
//            .add("device_info", getDeviceInfo()!!)
//    }
//
//    private fun getDeviceInfo(): String? {
//        if (mydeviceInfo == null) {
//            val jSONObject = JSONObject()
//            try {
//                jSONObject.put("imei", getImei())
//                jSONObject.put("udid", getUdid())
//                jSONObject.put("mac", "20:00:00:00:00:00")
//                jSONObject.put("imsi", getImsi())
//                jSONObject.put("android_id", getAndroidID())
//                jSONObject.put(
//                    "screen",
//                    getScreenResolution().replace("X","*")
//                )
//            } catch (e: JSONException) {
//                e.printStackTrace()
//            }
//            mydeviceInfo = jSONObject.toString()
//        }
//        return mydeviceInfo
//    }
//
//
//    private fun getSign(paramString: String): String {
//        return try {
//            val aesUtil = AESUtil("DmA1G5g5jq27L6OWbxapKln3CJ7HlyNBB6yOClNAMN6")
//            aesUtil.encrypt(paramString)
//        } catch (e: AesException) {
//            e.printStackTrace()
//            ""
//        }
//    }
//
//    private fun getSignString(paramString1: String, paramString2: String): String {
//        return "$paramString1|ahpagrcrf2p7m6rg|android|7.7.10||$paramString2"//todo:缺口加一个参数
//    }
//
//    @Synchronized
//    fun handleResponse(str: String) {
//        synchronized(AuthTools::class.java) {
//            runCatching {
//                JSONObject(str).apply {
//                    if (optInt("code") == 0) {
//                        optJSONObject("result")?.apply {
//                            optString("device_id").let {
//                                thefair_device_id = it
//                                thefairCid = it
//                            }
//                            thefairAuth = optString("access_token")
//                            optInt("server_timestamp").takeIf { it != 0 }?.let {
//                                tsDiff = it - (System.currentTimeMillis() / 1000L).toInt()
//                            }
//                            optString("refresh_token").let {
//                                if (it.isNotBlank()) refreshToken = it
//                            }
//                            refreshToken = optString("refresh_token")
//                        }
//                    }
//                }
//            }.onFailure {
//                it.printStackTrace() // 更好的错误处理
//            }
//        }
//    }
//
//    fun prepareRequest(request: Request?, paramString: String): Request {
//        val newBuilder = request?.newBuilder() ?: Request.Builder()
//        val build = FormBody.Builder()
//            .add("grant_type", paramString)
//            .build()
//
//        return newBuilder
//            .url("${ApiManager.TFEYE_BASE_URL}v1/system/auth/token?grant_type=$paramString")
//            .post(build)
//            .build()
//    }
//
//    @Synchronized
//    fun tokenSubSync(paramString: String): Response? {
//        return try {
//            val response =
//                ApiManager.tfEyeClient.newCall(prepareRequest(null, paramString)).execute()
//            handleResponse(response.peekBody(Long.MAX_VALUE).string())
//            response
//        } catch (e: IOException) {
//            e.printStackTrace()
//            null
//        }
//    }
//
//    fun isAuth(paramRequest: Request?): Boolean =
//        if (paramRequest?.url != null && paramRequest.url.toString()
//                .contains("system/auth/token")
//        ) true else false
//
//    fun handleTs(paramJSONObject: JSONObject) {
//        val result = paramJSONObject.optJSONObject("result") ?: return
//        val serverTimestamp = result.optInt("server_timestamp", 0)
//        if (serverTimestamp != 0) {
//            tsDiff = serverTimestamp - (System.currentTimeMillis() / 1000).toInt()
//        }
//    }
//}
//
