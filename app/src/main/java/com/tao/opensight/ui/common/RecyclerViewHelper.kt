package com.tao.opensight.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.tao.opensight.Const.ItemViewType.Companion.FOLLOW_CARD
import com.tao.opensight.Const.ItemViewType.Companion.TEXT_CARD_HEADER5
import com.tao.opensight.databinding.ItemFollowCardBinding


/**
 * RecyclerView帮助类，获取通用ViewHolder或ItemViewType。
 */
object ViewHolderFactory {
    fun create(parent: ViewGroup, viewType: Int) = when (viewType) {
        FOLLOW_CARD -> ViewHolder(
            ItemFollowCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        else ->EmptyViewHolder(parent)
    }
}
private class ViewHolder<T : ViewBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root) {
    fun <S> bind(item: S, bindFunction: T.(S) -> Unit) {
        binding.bindFunction(item)
    }
}
class EmptyViewHolder(view: View) : RecyclerView.ViewHolder(view)

