package com.tao.opensight.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.tao.opensight.Const.ItemViewType.Companion.FOLLOW_CARD
import com.tao.opensight.Const.ItemViewType.Companion.TEXT_CARD_HEADER5
import com.tao.opensight.databinding.ItemFollowCardBinding
import com.tao.opensight.databinding.ItemTextCardHeader5Binding
import com.tao.opensight.model.Daily


/**
 * RecyclerView帮助类，获取通用ViewHolder或ItemViewType。
 */
object ViewHolderFactory {
    fun create(parent: ViewGroup, viewType: Int) = when (viewType) {
        FOLLOW_CARD -> FollowCardViewHolder(parent.inflateBinding(ItemFollowCardBinding::inflate))

        TEXT_CARD_HEADER5 -> TextCardHeader5ViewHolder5(
            parent.inflateBinding(
                ItemTextCardHeader5Binding::inflate
            )
        )

        else -> EmptyViewHolder(View(parent.context))
    }
}


private fun <T : ViewBinding> ViewGroup.inflateBinding(
    bind: (LayoutInflater, ViewGroup?, Boolean) -> T,
    attachToParent: Boolean = false
): T {
    return bind(LayoutInflater.from(this.context), this, attachToParent)
}

