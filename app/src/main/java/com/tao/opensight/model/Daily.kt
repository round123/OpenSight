package com.tao.opensight.model

import com.tao.opensight.ext.conversionVideoDuration
import com.tao.opensight.ui.common.RecyclerViewHelper


/**
 * 首页-日报列表，响应实体类。
 *
 * @author vipyinzhiwei
 * @since  2020/5/9
 */
data class Daily(
    val itemList: List<Item>,
    val count: Int,
    val total: Int,
    val nextPageUrl: String?,
    val adExist: Boolean
) : Model() {

    data class Item(
        val type: String,
        val `data`: Data,
        val tag: Any?,
        val id: Int = 0,
        val adIndex: Int
    ) : Model(), DailyItemField {
        override fun getItemViewTpye(): Int =
            RecyclerViewHelper.getItemViewType(this)

        override fun getCoverFeed(): String = data.content.data.cover.feed
        override fun getAuthorAvatar(): String? = data.header.icon
        override fun getVideoDuration(): String =
            data.content.data.duration.conversionVideoDuration()

        override fun getDesc(): String = data.header.description
        override fun getTitle(): String = data.header.title

    }

    data class Data(
        val actionUrl: String?,
        val adTrack: Any,
        val backgroundImage: String,
        val content: Content,
        val dataType: String,
        val follow: Author.Follow?,
        val header: Header,
        val id: Int,
        val rightText: String,
        val subTitle: Any,
        val text: String,
        val titleList: List<String>,
        val type: String,
        val image: String,
        val label: Label?
    ) : Model()

    data class Header(
        val actionUrl: String?,
        val cover: Any,
        val description: String,
        val font: Any,
        val icon: String?,
        val iconType: String,
        val id: Int,
        val label: Any,
        val labelList: Any,
        val rightText: Any,
        val showHateVideo: Boolean,
        val subTitle: Any,
        val subTitleFont: Any,
        val textAlign: String,
        val time: Long,
        val title: String
    ) : Model()

}
