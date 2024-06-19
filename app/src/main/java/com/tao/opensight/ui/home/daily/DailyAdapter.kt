package com.tao.opensight.ui.home.daily

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.tao.opensight.databinding.ItemTextCardHeader5Binding
import com.tao.opensight.model.Daily
import com.tao.opensight.ui.common.FollowCardViewHolder
import com.tao.opensight.ui.common.TextCardHeader5ViewHolder
import com.tao.opensight.ui.common.ViewHolder
import com.tao.opensight.ui.common.ViewHolderFactory

class DailyAdapter() : PagingDataAdapter<Daily.Item, ViewHolder<*>>(DIFF_CALLBACK) {


    override fun onBindViewHolder(holder: ViewHolder<*>, position: Int) {
        val item=getItem(position)
        when (holder) {
            is FollowCardViewHolder -> holder.bind(item as Daily.Item)
            is TextCardHeader5ViewHolder ->holder.bind(item as Daily.Item)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<*> =
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