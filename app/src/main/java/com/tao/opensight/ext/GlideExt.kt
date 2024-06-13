package com.tao.opensight.ext

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadIcon(resourceId: Int) {
    Glide.with(this.context)
        .load(resourceId)
        .into(this)
}