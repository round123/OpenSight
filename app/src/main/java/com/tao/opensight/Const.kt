package com.tao.opensight

interface Const {
    interface ItemViewType {
        companion object {
            const val UNKNOWN = -1              //未知类型，使用EmptyViewHolder容错处理。
            const val FOLLOW_CARD = 14 //type:followCard -> dataType:FollowCard -> type:video -> dataType:VideoBeanForClient
            const val TEXT_CARD_HEADER5 = 5     //type:textCard -> dataType:TextCard -> type:header5
        }
    }
}