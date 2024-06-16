package com.tao.opensight.model


import com.flyco.tablayout.listener.CustomTabEntity

/**
 * 与CommonTabLayout搭配使用的实体类。
 *
 * @author vipyinzhiwei
 * @since  2020/5/10
 */
class TabEntity(private val title: String, private val selectedIcon: Int = 0, private val unSelectedIcon: Int = 0) : CustomTabEntity {

    override fun getTabTitle() = title

    override fun getTabSelectedIcon() = selectedIcon

    override fun getTabUnselectedIcon() = unSelectedIcon

}