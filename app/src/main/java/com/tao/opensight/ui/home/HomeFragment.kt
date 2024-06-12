package com.tao.opensight.ui.home

import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.flyco.tablayout.CommonTabLayout
import com.tao.opensight.ui.common.BaseFragment

class HomeFragment : BaseFragment() {

    private lateinit var logo: ImageView

    private lateinit var notice:ImageView

    private lateinit var viewPager2:ViewPager2

    private lateinit var tabLayout:CommonTabLayout

    override fun loadDataOnce() {

    }
}