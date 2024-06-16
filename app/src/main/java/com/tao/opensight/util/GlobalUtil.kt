package com.tao.opensight.util

import MMKVUtil
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import com.tao.opensight.App
import com.tao.opensight.ext.logW
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID

private const val TAG = "GlobalUtil"
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

/**
 *
 *
 * @return 设备的序列号。 uuid
 */
private var deviceSerial: String? = null
@SuppressLint("HardwareIds")
fun getDeviceSerial(): String {
    if (deviceSerial == null) {
        var deviceId: String? = null
            try {
                deviceId = Settings.Secure.getString(
                    App.context.contentResolver,
                    Settings.Secure.ANDROID_ID
                )
            } catch (e: Exception) {
                logW(TAG, "get android_id with error", e)
            }
            if (!TextUtils.isEmpty(deviceId) && deviceId!!.length < 255) {
                deviceSerial = deviceId
                return deviceSerial.toString()
            }

        var uuid = MMKVUtil.decode("uuid", "")
        if (!TextUtils.isEmpty(uuid)) {
            deviceSerial = uuid
            return deviceSerial.toString()
        }
         uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase(Locale.getDefault())
        CoroutineScope(Dispatchers.IO).launch { MMKVUtil.encode("uuid", uuid) }
        deviceSerial = uuid
        return deviceSerial.toString()
    } else {
        return deviceSerial.toString()
    }
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
        MMKVUtil.encode("screenResolution","${metrics.widthPixels}*${metrics.heightPixels}")
        screenResolution = "${metrics.widthPixels}*${metrics.heightPixels}"
    }
    return screenResolution
}