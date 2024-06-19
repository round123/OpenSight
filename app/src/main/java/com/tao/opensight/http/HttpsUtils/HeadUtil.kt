package com.tao.opensight.http.HttpsUtils

import MMKVDelegate
import MMKVUtil
import android.os.Build
import android.util.Log
import com.tao.opensight.ext.nullToEmpty
import com.tao.opensight.util.getScreenResolution
import com.tao.opensight.util.getUdid
import okhttp3.Cookie
import java.util.Locale


object HeadUtil {
    const val VERSION = "7070010"
    const val VERSION_CODE = "7.7.10"
    val MODEL = Build.MODEL
    val ANDROID_VERSION = Build.VERSION.RELEASE
    const val REGION = "cn-bj"
    val LOCALE = Locale.getDefault().toString()
    const val NETWORK_TYPE = "UNKNOWN"
    val DEVICES_ID by lazy { getUdid() }




    private var screenResolution: String? = null
    private var UAString: String? = null

    //todo:要实现登录功能需要修改CID，AUTH
//    fun getHeaders(): Map<String, String> {
//        val hashMap = HashMap<String, String>(8)
//        hashMap[X_THEFAIR_CID] = thefairCid.nullToEmpty()
//        hashMap[X_THEFAIR_APPID] = "ahpagrcrf2p7m6rg"
//        hashMap[X_THEFAIR_UA] = thefairUa.nullToEmpty()
//        hashMap[X_THEFAIR_AUTH] = thefairAuth.nullToEmpty()
//        return hashMap
//    }

    /**
     * 委托模式 设置值
     */
    var thefairCid: String? by MMKVDelegate("X-THEFAIR-CID", "")
    var thefairAuth: String? by MMKVDelegate("X-THEFAIR-AUTH", "")
    val thefairUa
        get()= userAgent
    val userAgent: String by lazy {
        val deviceInfo = "$MODEL;android;13;zh_CN;android;7.7.10;cn-bj;"
        val additionalInfo = ";${getScreenResolution()}"
        "EYEPETIZER/$VERSION ($deviceInfo;xiaomi;;$NETWORK_TYPE;$additionalInfo) native/1.0"
    }
    var thefair_device_id : String? by MMKVDelegate("THEFAIR_DEVICE_ID", "")
    var refreshToken:String? by MMKVDelegate("refresh_token","")
    var tsDiff:Int by MMKVDelegate("server_ts_diff",0)
    var wdj_auth:String by MMKVDelegate("WDJ_AUTH","")
    fun getTs(): Int {
        return tsDiff.plus(((System.currentTimeMillis() / 1000).toInt()))
    }

    private var c:Boolean?=null

    fun randomCookie(): Boolean? {
        if (c == null) c = java.lang.Boolean.valueOf(MMKVUtil.decode("IS_RANDOM_COOKIE", false))
        val stringBuilder = StringBuilder()
        stringBuilder.append("isRandomCookie: ")
        stringBuilder.append(c.toString())
        Log.d("random cookie", stringBuilder.toString())
        return c
    }
}
