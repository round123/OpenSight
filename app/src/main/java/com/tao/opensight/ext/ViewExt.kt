package com.tao.opensight.ext

import android.view.View

/**
 * 扩展函数，用于简化点击事件的设置。
 * @param action 点击时执行的动作，是一个接受View并返回Unit的lambda表达式。
 */
fun View.click(action: (View) -> Unit) {
    this.setOnClickListener { action(it) }
}
/**
 * 批量设置控件点击事件。
 *
 * @param v 点击的控件
 * @param block 处理点击事件回调代码块
 */
fun setOnClickListener(vararg v: View?, block: View.() -> Unit) {
    val listener = View.OnClickListener { it.block() }
    v.forEach { it?.setOnClickListener(listener) }
}

/**
 * 批量设置控件点击事件。
 *
 * @param v 点击的控件
 * @param listener 处理点击事件监听器
 */
fun setOnClickListener(vararg v: View?, listener: View.OnClickListener) {
    v.forEach { it?.setOnClickListener(listener) }
}