package com.tao.opensight

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * 应用模块: 宿主app
 * <p>
 * 类描述: 宿主app的 application ,在这里通过common组件中定义的组件生命周期配置类进行相应初始化(通过反射调用各个组件的初始化器)
 * <p>
 *
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
    }
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}