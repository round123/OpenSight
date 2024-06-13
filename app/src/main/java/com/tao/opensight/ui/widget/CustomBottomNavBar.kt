package com.tao.opensight.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.tao.opensight.R
import com.tao.opensight.ext.click
import com.tao.opensight.ext.loadIcon

class CustomBottomNavBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val homeIconView: ImageView by lazy { findViewById(R.id.home) }
    private val socialIconView: ImageView by lazy { findViewById(R.id.social) }
    private val moreIconView: ImageView by lazy { findViewById(R.id.more) }
    private val discoverIconView: ImageView by lazy { findViewById(R.id.discover) }
    private val mineIconView: ImageView by lazy { findViewById(R.id.mine) }

    var clickListener: ((Int) -> Unit)? = null

    companion object {
        const val HOME_PAGE = 0
        const val SQUARE_PAGE = 1
        const val MORE_PAGE = 2
        const val DISCOVER_PAGE = 3
        const val MINE_PAGE = 4
    }

    init {
        View.inflate(context, R.layout.layout_bottom_navigation, this)
        setClickListener(homeIconView, HOME_PAGE)
        setClickListener(socialIconView, SQUARE_PAGE)
        setClickListener(moreIconView, MORE_PAGE)
        setClickListener(discoverIconView, DISCOVER_PAGE)
        setClickListener(mineIconView, MINE_PAGE)
    }

    private fun setClickListener(iconView: ImageView, position: Int) {
        iconView.click {
            selectTab(position)
            clickListener?.invoke(position)
        }
    }

    private fun selectTab(position: Int) {
        if (position == MORE_PAGE) return

        homeIconView.loadIcon(getIconResId(HOME_PAGE, position))
        socialIconView.loadIcon(getIconResId(SQUARE_PAGE, position))
        discoverIconView.loadIcon(getIconResId(DISCOVER_PAGE, position))
        mineIconView.loadIcon(getIconResId(MINE_PAGE, position))
    }
    private fun getIconResId(page: Int, position: Int): Int {
        return when (page) {
            HOME_PAGE -> if (position == HOME_PAGE) R.mipmap.icon_tab_home_select else R.mipmap.icon_tab_home_unselect
            SQUARE_PAGE -> if (position == SQUARE_PAGE) R.mipmap.icon_tab_social_select else R.mipmap.icon_tab_social_unselect
            DISCOVER_PAGE -> if (position == DISCOVER_PAGE) R.mipmap.icon_tab_discover_select else R.mipmap.icon_tab_discover_unselect
            MINE_PAGE -> if (position == MINE_PAGE) R.mipmap.icon_tab_mine_select else R.mipmap.icon_tab_mine_unselect
            else -> throw IllegalArgumentException("Invalid page")
        }
    }
}