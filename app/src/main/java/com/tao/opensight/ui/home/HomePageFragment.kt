package com.tao.opensight.ui.home

import android.widget.ImageView

import androidx.viewpager2.widget.ViewPager2
import com.flyco.tablayout.CommonTabLayout

import com.tao.opensight.databinding.FragmentHomeBinding

import com.tao.opensight.ui.common.BaseFragment

class HomePageFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var logo: ImageView

    private lateinit var notice: ImageView

    private lateinit var viewPager2: ViewPager2

    private lateinit var tabLayout: CommonTabLayout
    override fun initViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }


    override fun loadDataOnce() {

    }

}