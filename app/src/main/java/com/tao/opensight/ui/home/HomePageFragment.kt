package com.tao.opensight.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope

import androidx.viewpager2.widget.ViewPager2
import com.flyco.tablayout.CommonTabLayout

import com.tao.opensight.databinding.FragmentHomeBinding
import com.tao.opensight.ext.click
import com.tao.opensight.http.ApiManager.MAINPAGE_SERVICE

import com.tao.opensight.ui.common.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        mBinding.titleBar.ivTabRight.click {
            lifecycleScope.launch {
                try {
                    val response = withContext(Dispatchers.IO) {
                        MAINPAGE_SERVICE.getDaily()
                    }
                    Log.d(TAG, "Response: $response")
                } catch (e: Exception) {
                    Log.e(TAG, "Error during network call", e)
                }
            }
        }
    }

    override fun loadDataOnce() {

    }

}