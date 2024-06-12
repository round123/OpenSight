package com.tao.opensight.ext

import android.view.View

/**
 * 扩展函数，用于简化点击事件的设置。
 * @param action 点击时执行的动作，是一个接受View并返回Unit的lambda表达式。
 */
fun View.click(action: (View) -> Unit) {
    this.setOnClickListener { action(it) }
}