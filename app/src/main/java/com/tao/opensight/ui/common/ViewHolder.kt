package com.tao.opensight.ui.common


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.tao.opensight.databinding.ItemFollowCardBinding
import com.tao.opensight.databinding.ItemTextCardHeader5Binding


/**
 * ViewHolder的密封类，
 */
class EmptyViewHolder(view: View) : RecyclerView.ViewHolder(view)

class FollowCardViewHolder(val mBinding : ItemFollowCardBinding) : RecyclerView.ViewHolder(mBinding.root)

class TextCardHeader5ViewHolder5(val mBinding : ItemTextCardHeader5Binding) : RecyclerView.ViewHolder(mBinding.root)
