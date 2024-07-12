package com.tao.opensight.ui

import android.os.Bundle
import androidx.navigation.findNavController
import com.tao.opensight.R
import com.tao.opensight.databinding.LayoutMainactivityBinding
import com.tao.opensight.ui.common.BaseActivity
import com.tao.opensight.ui.widget.CustomBottomNavBar.Companion.DISCOVER_PAGE
import com.tao.opensight.ui.widget.CustomBottomNavBar.Companion.HOME_PAGE
import com.tao.opensight.ui.widget.CustomBottomNavBar.Companion.MINE_PAGE
import com.tao.opensight.ui.widget.CustomBottomNavBar.Companion.MORE_PAGE
import com.tao.opensight.ui.widget.CustomBottomNavBar.Companion.SQUARE_PAGE

class MainActivity : BaseActivity<LayoutMainactivityBinding>() {
    override fun initViewBinding(): LayoutMainactivityBinding {
        return LayoutMainactivityBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        initBottomNav()
    }

    private fun initBottomNav(){
        val navController=findNavController(R.id.fragmentContainerView)
        mBinding.bottomNavBar.clickListener = { position: Int ->
            when (position) {
                HOME_PAGE -> navController.navigate(R.id.homeFragment)
                SQUARE_PAGE -> navController.navigate(R.id.squareFragment)
                MORE_PAGE -> 0
                DISCOVER_PAGE -> navController.navigate(R.id.discoverFragment)
                MINE_PAGE -> navController.navigate(R.id.mineFragment)
                // 可以继续添加其他的条件分支来处理不同的按钮点击
            }
        }
    }

}