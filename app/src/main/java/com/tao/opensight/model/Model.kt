package com.tao.opensight.model

/**
 * 所有网络通讯数据模型实体类的基类。
 *
 * @author vipyinzhiwei
 * @since  2020/5/10
 */
abstract class Model


interface DailyItemField {
    abstract fun getItemViewTpye() :Int
    abstract fun getCoverFeed(): String
    abstract fun getAuthorAvatar(): String?
    abstract fun getVideoDuration(): String
    abstract fun getDesc(): String
    abstract fun getTitle(): String
    abstract fun getTextCardText():String
}