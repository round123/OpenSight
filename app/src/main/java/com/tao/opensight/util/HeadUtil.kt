package com.tao.opensight.util

import MMKVDelegate
import MMKVUtil
import android.os.Build
import android.util.Log
import com.blankj.utilcode.util.LogUtils.a
import com.tao.opensight.ext.nullToEmpty
import okhttp3.Cookie
import okhttp3.RequestBody
import okio.Buffer
import java.io.IOException
import java.util.Locale


object HeadUtil {
    const val VERSION = "7070010"
    const val VERSION_CODE = "7.7.10"
    val MODEL = Build.MODEL
    val ANDROID_VERSION = Build.VERSION.RELEASE
    const val REGION = "cn-bj"
    val LOCALE = Locale.getDefault().toString()
    const val NETWORK_TYPE = "UNKNOWN"
    val DEVICES_ID by lazy { getDeviceSerial() }

    private var screenResolution: String? = null
    private var UAString: String? = null

    //todo:要实现登录功能需要修改CID，AUTH
    fun getHeaders(): Map<String, String> {
        val hashMap = HashMap<String, String>(8)
        hashMap["X-THEFAIR-CID"] = thefairCid.nullToEmpty()
        hashMap["X-THEFAIR-APPID"] = "ahpagrcrf2p7m6rg"
        hashMap["X-THEFAIR-UA"] = thefairUa.nullToEmpty()
        hashMap["X-THEFAIR-AUTH"] = thefairAuth.nullToEmpty()
        return hashMap
    }

    /**
     * 委托模式 设置值
     */
    var thefairCid: String? by MMKVDelegate("X-THEFAIR-CID", "")
    var thefairAuth: String? by MMKVDelegate("X-THEFAIR-AUTH", "")
    val thefairUa
        get()= userAgent
    val userAgent: String by lazy {
        val deviceInfo = "$MODEL;$ANDROID_VERSION;$VERSION_CODE;$LOCALE;android;$VERSION_CODE;$REGION;"
        val additionalInfo = ";${getScreenResolution()}"
        "EYEPETIZER/$VERSION ($deviceInfo$REGION;$DEVICES_ID;$NETWORK_TYPE;$additionalInfo) native/1.0"
    }
    var refreshToken:String? by MMKVDelegate("refresh_token","")
    var tsDiff:Int? by MMKVDelegate("server_ts_diff",0)

    val responseCookies: HashSet<Cookie> = HashSet()
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
