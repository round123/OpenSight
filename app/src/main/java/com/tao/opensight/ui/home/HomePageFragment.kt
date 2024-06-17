package com.tao.opensight.ui.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope

import androidx.viewpager2.widget.ViewPager2
import com.flyco.tablayout.CommonTabLayout

import com.tao.opensight.databinding.FragmentHomeBinding
import com.tao.opensight.ext.click
import com.tao.opensight.http.ApiManager.MAINPAGE_SERVICE

import com.tao.opensight.ui.common.BaseFragment
import com.tao.opensight.util.AESUtil
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.titleBar.ivTabRight.click {
            val aesUtil = AESUtil("DmA1G5g5jq27L6OWbxapKln3CJ7HlyNBB6yOClNAMN6")
            val str =aesUtil.decrypt("TI3PUSy/+5ev+CGa5pAOiw8bFG6uRhMK8RzE30c4muJ8Nbym1lb2kyFU3Gb2I7DoaExA/CmziUcH/+GWSkgyahB0tlWj4piMLsrw3Llc7mQFIIAX1F5zxA8PrKeOLjVf")
            Log.d(TAG, str)
        }
    }

    override fun loadDataOnce() {

    }

}