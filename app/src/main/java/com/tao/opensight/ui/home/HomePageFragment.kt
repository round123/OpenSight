package com.tao.opensight.ui.home


import android.os.Bundle
import android.util.Log

import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope


import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.ApiUtils.Api
import com.flyco.tablayout.CommonTabLayout


import com.tao.opensight.databinding.FragmentHomeBinding


import com.tao.opensight.ui.common.BaseFragment
import com.tao.opensight.ui.home.daily.DailyFragment


class HomePageFragment : BaseFragment<FragmentHomeBinding>() {

    override val TAG = this.javaClass.simpleName
    private val adapter: HomePageVPAdapter by lazy {
        HomePageVPAdapter(requireActivity()).apply {
            addFragments(
                createFragments
            )
        }
    }
    private val createFragments: Array<Fragment> = arrayOf(DailyFragment.newInstance())
    override fun initViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.viewpager.adapter=adapter
    }

    override fun loadDataOnce() {
    }

}