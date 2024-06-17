package com.tao.opensight.util

import MMKVUtil
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.preference.PreferenceManager
import android.telephony.TelephonyManager
import android.text.TextUtils
import com.blankj.utilcode.util.PhoneUtils
import com.tao.opensight.App
import com.tao.opensight.ext.logW
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID


private const val TAG = "GlobalUtil"

const val VersionCode=7070010
const val VersionName="7.7.10"
const val Channel="opensource"
/**
 * 获取设备的品牌，如果无法获取到，则返回Unknown。
 * @return 设备品牌，全部转换为小写格式。
 */
val deviceBrand: String
    get() {
        var deviceBrand = Build.BRAND
        if (TextUtils.isEmpty(deviceBrand)) {
            deviceBrand = "unknown"
        }
        return deviceBrand.toLowerCase(Locale.getDefault())
    }
/**
 * 获取当前应用程序的包名。
 *
 * @return 当前应用程序的包名。
 */
val appPackage: String
    get() = App.context.packageName
/**
 * 获取AndroidManifest.xml文件中，<application>标签下的meta-data值。
 *
 * @param key
 *  <application>标签下的meta-data健
 */
fun getApplicationMetaData(key: String): String? {
    var applicationInfo: ApplicationInfo? = null
    try {
        applicationInfo = App.context.packageManager.getApplicationInfo(appPackage, PackageManager.GET_META_DATA)
    } catch (e: PackageManager.NameNotFoundException) {
        logW(TAG, e.message, e)
    }
    if (applicationInfo == null) return ""
    return applicationInfo.metaData.getString(key)
}


// 使用注解忽略硬件ID的警告
@SuppressLint("HardwareIds")
fun getUdid(): String {
    // 尝试从 MMKV 中获取已存储的 UUID
    var uuid = MMKVUtil.decode("udid", "")
    if (!TextUtils.isEmpty(uuid)) {
        return uuid
    }
    // 如果 MMKV 中没有 UUID，则生成一个新的 UUID，移除所有破折号并转换为大写
    uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase(Locale.getDefault())
    // 异步存储新生成的 UUID 到 MMKV
    CoroutineScope(Dispatchers.IO).launch {
        MMKVUtil.encode("udid", uuid)
    }
    return uuid
}
/**
 *
 *
 * @return 分辨率。
 */
fun getScreenResolution(): String {
    var screenResolution = MMKVUtil.decode("screenResolution","")
    if (screenResolution == "") {
        val metrics = App.context.resources.displayMetrics
        MMKVUtil.encode("screenResolution","${metrics.widthPixels}X${metrics.heightPixels}")
        screenResolution = "${metrics.widthPixels}X${metrics.heightPixels}"
    }
    return screenResolution
}

fun getImei(): String {
    return "android_id"
}
fun getImsi():String{
    return ""
}
