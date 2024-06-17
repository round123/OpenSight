package com.tao.opensight.http.auth


import com.tao.opensight.ext.nullToEmpty
import com.tao.opensight.http.ApiManager
import com.tao.opensight.util.AESUtil
import com.tao.opensight.util.AesException
import com.tao.opensight.util.HeadUtil.getTs
import com.tao.opensight.util.HeadUtil.refreshToken
import com.tao.opensight.util.HeadUtil.thefairAuth
import com.tao.opensight.util.HeadUtil.thefairCid
import com.tao.opensight.util.HeadUtil.tsDiff
import com.tao.opensight.util.getDeviceSerial
import com.tao.opensight.util.getScreenResolution
import org.json.JSONObject
import java.io.IOException


object AuthTools {
//    var mydeviceInfo: String? = null
//
//    fun fillBuilder(
//        paramBuilder: FormBody.Builder,
//        paramString: String,
//    ): FormBody.Builder {
//        val ts= getTs()
//        val sign = getSign(getSignString(paramString, str1))
//        if ("refresh_token" == paramString) {
//            paramBuilder.add("refresh_token", refreshToken.nullToEmpty())
//        }
//        return paramBuilder.add("grant_type", paramString)
//            .add("sign", sign)
//            .add("ts", ts)
//            .add("device_info", getDeviceInfo())
//    }
//
//    private fun getDeviceInfo(): String {
//        if (mydeviceInfo == null) {
//            val jsonObject = buildJsonObject {
//                put("imei", "")
//                put("udid", getDeviceSerial())
//                put("mac", "20:00:00:00:00:00")
//                put("imsi", "0")
//                put("android_id", "")
//                put("screen", getScreenResolution())
//            }
//            mydeviceInfo = jsonObject.toString()
//        }
//        return mydeviceInfo!!
//    }
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
//        return "$paramString1|ahpagrcrf2p7m6rg|android|7.7.10|$thefair_device_id|$paramString2"
//    }
//
//
//    //处理请求，设置后续接口要的header
//    @Synchronized
//    fun handleResponse(paramString: String) {
//        kotlin.runCatching {
//           val jsonObject = JSONObject(paramString)
//            if (jsonObject.optInt("code") == 0) {
//                val result = jsonObject.optJSONObject("result") ?: return
//                result?.let { result ->
//                    thefairCid = result.device_id ?: ""
                //thefair_device_id= result.device_id ?: ""
                    //TODO:持久化device_id，key:THEFAIR_DEVICE_ID
//                    thefairAuth = result.access_token ?: ""
//
//                    result.refresh_token?.let {
//                        if (it.isNotBlank()) refreshToken = it
//                    }
//
//                    result.server_timestamp.takeIf { it != 0 }?.let {
//                        tsDiff = it - (System.currentTimeMillis() / 1000L).toInt()
//                    }
//                }
//            }
//        }
//    }
//
//    fun prepareRequest(paramRequest: Request?, paramString: String): Request {
//        val formBody = FormBody.Builder().add("grant_type", paramString).build()
//        val builder = paramRequest?.newBuilder() ?: Request.Builder()
//        val urlString =
//            "${ApiManager.TFEYE_BASE_URL}v1/system/auth/token?grant_type=$paramString"
//        return builder.url(urlString).post(formBody).build()
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
//         if (paramRequest?.url != null && paramRequest.url.toString()
//                .contains("system/auth/token")
//        ) true else false
//    fun handleTs(paramJSONObject: JSONObject) {
//       val result = jsonObject.optJSONObject("result") ?: return
//        val serverTimestamp = result.optInt("server_timestamp", 0)
//          if (serverTimestamp != 0) {
//         tsDiff = serverTimestamp - (System.currentTimeMillis() / 1000).toInt()
//
//          }
//        }
//    }
}
