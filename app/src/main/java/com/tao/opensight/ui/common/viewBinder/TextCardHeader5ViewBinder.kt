package com.tao.opensight.ui.common.viewBinder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.drakeet.multitype.ItemViewBinder
import com.tao.opensight.databinding.ItemTextCardHeader5Binding
import com.tao.opensight.model.DailyItemField

class TextCardHeader5ViewBinder :
    ItemViewBinder<DailyItemField, TextCardHeader5ViewBinder.ViewHolder>() {


    class ViewHolder(val mBinding: ItemTextCardHeader5Binding) :
        RecyclerView.ViewHolder(mBinding.root)

    override fun onBindViewHolder(holder: ViewHolder, item: DailyItemField) {
        holder.mBinding.apply { vHeaderTitle.text = item.getTitle() }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val binding = ItemTextCardHeader5Binding.inflate(inflater, parent, false)
        return TextCardHeader5ViewBinder.ViewHolder(binding)
    }

}