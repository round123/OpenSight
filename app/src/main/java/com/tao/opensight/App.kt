package com.tao.opensight

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import com.tencent.mmkv.MMKV


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
        val rootDir = MMKV.initialize(this)
        Log.d("MMKV", "MMKV root directory: $rootDir")
    }
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}