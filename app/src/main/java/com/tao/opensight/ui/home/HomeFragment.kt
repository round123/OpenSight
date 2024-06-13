package com.tao.opensight.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.flyco.tablayout.CommonTabLayout
import com.tao.opensight.R
import com.tao.opensight.databinding.FragmentHomeBinding
import com.tao.opensight.ui.common.BaseFragment

class HomeFragment : Fragment() {

    private lateinit var logo: ImageView

    private lateinit var notice:ImageView

    private lateinit var viewPager2:ViewPager2

    private lateinit var tabLayout:CommonTabLayout



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}