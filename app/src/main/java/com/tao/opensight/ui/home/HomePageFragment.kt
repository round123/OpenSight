package com.tao.opensight.ui.home


import android.os.Bundle
import android.util.Log

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope


import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.ApiUtils.Api
import com.flyco.tablayout.CommonTabLayout


import com.tao.opensight.databinding.FragmentHomeBinding


import com.tao.opensight.ui.common.BaseFragment



class HomePageFragment : BaseFragment<FragmentHomeBinding>() {

    override val TAG = this.javaClass.simpleName

    private lateinit var logo: ImageView

    private lateinit var notice: ImageView

    private lateinit var viewPager2: ViewPager2

    private lateinit var tabLayout: CommonTabLayout
    override fun initViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun loadDataOnce() {

    }

}