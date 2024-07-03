package com.tao.opensight.ui.common.viewBinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.tao.opensight.model.DailyItemField


class EmptyItemViewBinder : ItemViewBinder<DailyItemField, EmptyItemViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        // 创建一个不可见的空视图
        val view = View(parent.context)
        view.layoutParams = ViewGroup.LayoutParams(0, 0)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: DailyItemField) {
        // 不需要绑定任何数据
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}