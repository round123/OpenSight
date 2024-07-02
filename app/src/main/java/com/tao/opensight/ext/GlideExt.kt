package com.tao.opensight.ext

import android.view.View
import android.widget.ImageView
import com.blankj.utilcode.util.ConvertUtils.dp2px
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.tao.opensight.R
import com.tao.opensight.model.Url

fun ImageView.loadIcon(resourceId: Int) {
    Glide.with(this.context)
        .load(resourceId)
        .placeholder(R.drawable.white_background)
        .into(this)
}
/**
 * Glide加载图片，可以指定圆角弧度。
 *
 * @param url 图片地址
 * @param round 圆角，单位dp
 */
fun View.load(url: String, roundDp: Float = 0f) {
    (this as? ImageView)?.let { imageView ->
        val requestOptions = if (roundDp > 0f) {
            RequestOptions.bitmapTransform(RoundedCorners(dp2px(roundDp)))
                .placeholder(R.drawable.white_background)
        } else {
            RequestOptions.placeholderOf(R.drawable.white_background)
        }

        Glide.with(imageView.context)
            .load(url)
            .apply(requestOptions)
            .into(imageView)
    }
}

fun View.loadImage(url: String, circleCrop: Boolean = false) {
    (this as? ImageView)?.let {
            imageView ->
        val requestOptions = if (circleCrop) {
        RequestOptions().transform(CircleCrop()).placeholder(R.drawable.white_background)
    }
    
    } else {
        RequestOptions().placeholder(R.drawable.placeholder)
    }

    Glide.with(this.context)
        .load(url)
        .apply(requestOptions)
        .into(this)
}

/**
 * Glide加载图片，可以定义配置参数。
 *
 * @param url 图片地址
 * @param options 配置参数
 */
fun View.load(url: String, options: RequestOptions.() -> Unit) {
    if (this is ImageView) {
        val requestOptions = RequestOptions().apply(options)
        Glide.with(this.context).load(url).placeholder(R.drawable.white_background)
            .apply(requestOptions).into(this)
    }
}