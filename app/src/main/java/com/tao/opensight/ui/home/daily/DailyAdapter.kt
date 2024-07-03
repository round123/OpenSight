package com.tao.opensight.ui.home.daily

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.MultiTypeAdapter
import com.tao.opensight.Const.ItemViewType.Companion.FOLLOW_CARD
import com.tao.opensight.Const.ItemViewType.Companion.TEXT_CARD_HEADER5
import com.tao.opensight.ext.click
import com.tao.opensight.ext.load
import com.tao.opensight.model.Daily
import com.tao.opensight.model.DailyItemField
import com.tao.opensight.ui.common.FollowCardViewHolder
import com.tao.opensight.ui.common.RecyclerViewHelper
import com.tao.opensight.ui.common.TextCardHeader5ViewHolder
import com.tao.opensight.ui.common.ViewHolderFactory
import com.tao.opensight.ui.common.viewBinder.EmptyItemViewBinder
import com.tao.opensight.ui.common.viewBinder.FollowCardViewBinder
import com.tao.opensight.ui.common.viewBinder.TextCardHeader5ViewBinder

class DailyAdapter() : PagingDataAdapter<Daily.Item, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int =
        RecyclerViewHelper.getItemViewType(getItem(position)!!)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)!!
        when (holder) {
            is FollowCardViewHolder -> holder.mBinding.apply {
                vCover.load(item.getCoverFeed())
                // 加载作者头像，确保头像不为空，使用 getAuthorAvatar() 方法
                vAuthorAvatar.load(item.getAuthorAvatar() ?: "")
                // 设置视频时长文本，使用 getVideoDuration() 方法
                vTimeStamp.text = item.getVideoDuration()
                // 设置描述和标题，使用 getDesc() 和 getTitle() 方法
                vDesc.text = item.getDesc()
                vTitle.text = item.getTitle()
                // 设置点击事件
                holder.itemView.click {
                    //todo:跳转视频页
                }
            }

            is TextCardHeader5ViewHolder -> holder.mBinding.apply {
                vHeaderTitle.text = item.getTitle()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolderFactory.create(parent, viewType)

    companion object {
        const val TAG = "DailyAdapter"
        const val DEFAULT_LIBRARY_TYPE = "DEFAULT"
        const val NONE_LIBRARY_TYPE = "NONE"
        const val DAILY_LIBRARY_TYPE = "DAILY"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Daily.Item>() {

            override fun areItemsTheSame(oldItem: Daily.Item, newItem: Daily.Item) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Daily.Item, newItem: Daily.Item) =
                oldItem == newItem
        }
    }
}