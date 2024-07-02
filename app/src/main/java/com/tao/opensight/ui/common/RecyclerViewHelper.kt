package com.tao.opensight.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.tao.opensight.Const.ItemViewType.Companion.FOLLOW_CARD
import com.tao.opensight.Const.ItemViewType.Companion.TEXT_CARD_HEADER5
import com.tao.opensight.Const.ItemViewType.Companion.UNKNOWN
import com.tao.opensight.databinding.ItemFollowCardBinding
import com.tao.opensight.databinding.ItemTextCardHeader5Binding
import com.tao.opensight.model.Daily
import com.tao.opensight.model.Discovery
import com.tao.opensight.model.Follow
import com.tao.opensight.model.HomePageRecommend
import com.tao.opensight.model.VideoRelated
import com.tao.opensight.model.VideoReplies


/**
 * RecyclerView帮助类，获取通用ViewHolder或ItemViewType。
 */
object ViewHolderFactory {
    fun create(parent: ViewGroup, viewType: Int) = when (viewType) {

        FOLLOW_CARD -> FollowCardViewHolder(parent.inflateBinding(ItemFollowCardBinding::inflate))

        TEXT_CARD_HEADER5 -> TextCardHeader5ViewHolder(
            parent.inflateBinding(
                ItemTextCardHeader5Binding::inflate
            )
        )

        else -> EmptyViewHolder(View(parent.context))
    }
}

object RecyclerViewHelper {
    fun getItemViewType(type: String, dataType: String) = when (type) {
        "followCard" -> {
            when (dataType) {
                "FollowCard" -> FOLLOW_CARD
                else -> UNKNOWN
            }

        }

        else -> UNKNOWN
    }

    private fun getTextCardType(type: String) = when (type) {
        //"header4" -> TEXT_CARD_HEADER4
        "header5" -> TEXT_CARD_HEADER5
        //"header7" -> TEXT_CARD_HEADER7
        //"header8" -> TEXT_CARD_HEADER8
        //"footer2" -> TEXT_CARD_FOOTER2
        //"footer3" -> TEXT_CARD_FOOTER3
        else -> UNKNOWN
    }

    //发现页textCard
    fun getItemViewType(item: Discovery.Item): Int {
        return if (item.type == "textCard") getTextCardType(item.data.type) else getItemViewType(
            item.type,
            item.data.dataType
        )
    }

    //首页推荐页textCard
    fun getItemViewType(item: HomePageRecommend.Item): Int {
        return if (item.type == "textCard") getTextCardType(item.data.type) else getItemViewType(
            item.type,
            item.data.dataType
        )
    }

    //首页日报textCard
    fun getItemViewType(item: Daily.Item): Int {
        return if (item.type == "textCard") getTextCardType(item.data.type) else getItemViewType(
            item.type,
            item.data.dataType
        )
    }

    //关注页textCard
    fun getItemViewType(item: Follow.Item): Int {
        return if (item.type == "textCard") getTextCardType(item.data.type) else getItemViewType(
            item.type,
            item.data.dataType
        )
    }

    private fun extracted() {
        fun getItemViewType(item: Follow.Item): Int {
            return if (item.type == "textCard") getTextCardType(item.data.type) else getItemViewType(
                item.type,
                item.data.dataType
            )
        }
    }

    //音频页textCard
    fun getItemViewType(item: VideoRelated.Item): Int {
        return if (item.type == "textCard") getTextCardType(item.data.type) else getItemViewType(
            item.type,
            item.data.dataType
        )
    }

    //音频回复textCard
    fun getItemViewType(item: VideoReplies.Item): Int {
        return if (item.type == "textCard") getTextCardType(item.data.type) else getItemViewType(
            item.type,
            item.data.dataType
        )
    }

}


private fun <T : ViewBinding> ViewGroup.inflateBinding(
    bind: (LayoutInflater, ViewGroup?, Boolean) -> T,
    attachToParent: Boolean = false
): T {
    return bind(LayoutInflater.from(this.context), this, attachToParent)
}

