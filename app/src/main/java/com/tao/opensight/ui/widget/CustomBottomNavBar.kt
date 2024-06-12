package com.tao.opensight.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.tao.opensight.R
import com.tao.opensight.ext.click


class CustomBottomNavBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    var clickListener: ((Int) -> Unit)? = null  //让外部决定要做什么

    companion object {
        private const val HOME_PAGE = 0
        private const val SOCIAL_PAGE = 1
        private const val MORE_PAGE = 2
        private const val DISCOVER_PAGE = 3
        private const val MINE_PAGE = 4
    }

    init {
        View.inflate(context, R.layout.layout_bottom_navigation, this)
        // 初始化各个按钮的点击事件
        findViewById<ImageView>(R.id.home).click {
            selectTab(HOME_PAGE)
            clickListener?.invoke(HOME_PAGE)
        }
        findViewById<ImageView>(R.id.social).click {
            selectTab(SOCIAL_PAGE)
            clickListener?.invoke(SOCIAL_PAGE)
        }
        findViewById<ImageView>(R.id.more).click {
            selectTab(MORE_PAGE)
            clickListener?.invoke(MORE_PAGE)
        }
        findViewById<ImageView>(R.id.discover).click {
            selectTab(DISCOVER_PAGE)
            clickListener?.invoke(DISCOVER_PAGE)
        }
        findViewById<ImageView>(R.id.mine).setOnClickListener {
            selectTab(MINE_PAGE)
            clickListener?.invoke(MINE_PAGE)
        }
    }

    private fun selectTab(position: Int) {
        if (position == MORE_PAGE) return
        for (i in 0 until childCount) {
            // 获取子视图并更新状态
            val child = getChildAt(i)
            val icon = when (i) {
                HOME_PAGE -> if (position == HOME_PAGE) R.mipmap.icon_tab_home_select else R.mipmap.icon_tab_home_unselect
                SOCIAL_PAGE -> if (position == SOCIAL_PAGE) R.mipmap.icon_tab_social_select else R.mipmap.icon_tab_social_unselect
                DISCOVER_PAGE -> if (position == DISCOVER_PAGE) R.mipmap.icon_tab_discover_select else R.mipmap.icon_tab_discover_unselect
                MINE_PAGE -> if (position == MINE_PAGE) R.mipmap.icon_tab_mine_select else R.mipmap.icon_tab_mine_unselect
                else -> throw IllegalStateException("Unexpected position: $i")
            }
            // 更新图标
            (child as? ImageView)?.setImageDrawable(ContextCompat.getDrawable(context, icon))
        }
    }
}