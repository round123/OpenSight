package com.tao.opensight.http.HttpsUtils

import com.tao.opensight.util.getUdid
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.util.concurrent.ConcurrentHashMap

/**
 * Cookie类，cookie自动添加ky_udid=${getDeviceSerial()};ky_auth=头部
 */
class CustomCookieJar : CookieJar {
    private val cookieStore = ConcurrentHashMap<String, MutableList<Cookie>>()

    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
        val cookies = cookieStore[url.host] ?: mutableListOf()
        val fixedCookies = "${getUdid()};ky_auth=" // 首先生成固定的 Cookie
        var cookieValue = cookies.joinToString(separator = "; ") { "${it.name}=${it.value}" }
        cookieValue = if (cookieValue.isNotEmpty()) {
            "$fixedCookies; $cookieValue" // 将固定的 Cookie 放在前面，然后添加其他 Cookie
        } else {
            fixedCookies
        }
        return mutableListOf(
            Cookie.Builder()
                .domain(url.host)
                .path("/")
                .name("ky_udid")// 这里需要考虑是否每次都应该使用 "ky_auth" 作为 Cookie 名
                .value(cookieValue)
                .build()
        )
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        val existingCookies = cookieStore[url.host] ?: mutableListOf()
        cookies.forEach { newCookie ->
            val index = existingCookies.indexOfFirst { it.name == newCookie.name }
            if (index != -1) {
                existingCookies[index] = newCookie // Replace the existing cookie
            } else {
                existingCookies.add(newCookie) // Add new cookie if not present
            }
        }
        cookieStore[url.host] = existingCookies
    }

}
