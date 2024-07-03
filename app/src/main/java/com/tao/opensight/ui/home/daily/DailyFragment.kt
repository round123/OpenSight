package com.tao.opensight.ui.home.daily

import android.os.Bundle
import android.view.View
import com.tao.opensight.databinding.FragmentDailyBinding

import com.tao.opensight.ui.common.BaseFragment

class DailyFragment:BaseFragment<FragmentDailyBinding>() {

    override val TAG = this::class.java.simpleName



    override fun initViewBinding(): FragmentDailyBinding {
        return FragmentDailyBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun loadDataOnce() {

    }
}