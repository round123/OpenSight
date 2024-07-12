package com.tao.opensight.ext

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tao.opensight.App

/**
 * 弹出Toast提示。
 *
 * @param duration 显示消息的时间  Either {@link #LENGTH_SHORT} or {@link #LENGTH_LONG}
 */
fun Int.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(App.context, this, duration).show()
}

/**
 * 解析xml布局
 *
 * @param parent 父布局
 * @param attachToRoot 是否依附到父布局
 */
fun Int.inflate(parent: ViewGroup, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(parent.context).inflate(this, parent, attachToRoot)
}

/**
 * 获取转换后的时间样式。
 *
 * @return 处理后的时间样式，示例：06:50
 */
fun Int.conversionVideoDuration(): String {
    val secondsPerMinute = 60
    val secondsPerHour = 3600
    val secondsPerDay = 86400

    return when {
        this < secondsPerHour -> "${this / secondsPerMinute}:${this % secondsPerMinute}"
        this < secondsPerDay -> "${this / secondsPerHour}:${(this % secondsPerHour) / secondsPerMinute}:${this % secondsPerMinute}"
        else -> {
            val days = this / secondsPerDay
            val hours = (this % secondsPerDay) / secondsPerHour
            val minutes = (this % secondsPerHour) / secondsPerMinute
            val seconds = this % secondsPerMinute
            "${days}d $hours:$minutes:$seconds"
        }
    }.padStart(2, '0')  // 确保格式化输出至少两位数
}

fun  Int.dp2px() : Int {
    val scale = Resources.getSystem().displayMetrics.density
    return (this * scale + 0.5f).toInt()
}