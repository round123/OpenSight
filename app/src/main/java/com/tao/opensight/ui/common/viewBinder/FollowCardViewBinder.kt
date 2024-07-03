package com.tao.opensight.ui.common.viewBinder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.tao.opensight.databinding.ItemFollowCardBinding
import com.tao.opensight.ext.click
import com.tao.opensight.ext.load
import com.tao.opensight.model.DailyItemField

class FollowCardViewBinder() : ItemViewBinder<DailyItemField, FollowCardViewBinder.ViewHolder>() {

    class ViewHolder(val mBinding: ItemFollowCardBinding) : RecyclerView.ViewHolder(mBinding.root)

    override fun onBindViewHolder(holder: ViewHolder, item: DailyItemField) {
        holder.mBinding.apply {
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
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val binding = ItemFollowCardBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
}