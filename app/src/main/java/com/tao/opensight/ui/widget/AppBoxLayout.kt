package com.tao.opensight.ui.widget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.tao.opensight.R
import com.tao.opensight.ui.widget.CustomBottomNavBar.Companion.DISCOVER_PAGE
import com.tao.opensight.ui.widget.CustomBottomNavBar.Companion.HOME_PAGE
import com.tao.opensight.ui.widget.CustomBottomNavBar.Companion.MINE_PAGE
import com.tao.opensight.ui.widget.CustomBottomNavBar.Companion.MORE_PAGE
import com.tao.opensight.ui.widget.CustomBottomNavBar.Companion.SQUARE_PAGE

class AppBoxLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr) {

    private val homeActivityFragContainer: FrameLayout by lazy { findViewById(R.id.fragmentContainerView) }
    private val bottomNavBar: CustomBottomNavBar by lazy { findViewById(R.id.bottomNavBar) }

    init {
        View.inflate(context, R.layout.layout_appbox, this)
        bottomNavBar.clickListener = { position ->
            setSection(position)
        }
    }

    private fun setSection(position: Int) {
        val navController = homeActivityFragContainer.findNavController()
        when (position) {
            HOME_PAGE -> navController.navigate(R.id.homeFragment)
            SQUARE_PAGE -> navController.navigate(R.id.squareFragment)
            MORE_PAGE -> 0
            DISCOVER_PAGE -> navController.navigate(R.id.discoverFragment)
            MINE_PAGE -> navController.navigate(R.id.mineFragment)
        }
    }

}