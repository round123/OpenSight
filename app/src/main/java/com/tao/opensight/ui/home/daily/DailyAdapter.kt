package com.tao.opensight.ui.home.daily

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tao.opensight.ext.load
import com.tao.opensight.model.Daily
import com.tao.opensight.ui.common.FollowCardViewHolder
import com.tao.opensight.ui.common.RecyclerViewHelper
import com.tao.opensight.ui.common.TextCardHeader5ViewHolder

import com.tao.opensight.ui.common.ViewHolderFactory

class DailyAdapter() : PagingDataAdapter<Daily.Item, RecyclerView.ViewHolder>(DIFF_CALLBACK) {


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)!!
        when (holder) {
            is FollowCardViewHolder -> holder.mBinding.apply {
                vCover.load(item.data.content.data.cover.feed)
                vAuthorAvatar.load(item.data.header.icon ?: "")
            }
            is TextCardHeader5ViewHolder -> holder.mBinding
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolderFactory.create(parent, viewType)

    override fun getItemViewType(position: Int) =
        RecyclerViewHelper.getItemViewType(getItem(position)!!)

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