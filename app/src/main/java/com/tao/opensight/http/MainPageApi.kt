package com.tao.opensight.http

import com.tao.opensight.model.CommunityRecommend
import com.tao.opensight.model.Daily
import com.tao.opensight.model.Discovery
import com.tao.opensight.model.Follow
import com.tao.opensight.model.HomePageRecommend
import com.tao.opensight.model.PushMessage
import retrofit2.http.GET
import retrofit2.http.Url

interface MainPageApi {
    /**
     * 首页-发现列表
     */
    @GET("api/v7/index/tab/discovery")
    suspend fun getDiscovery(): Discovery

    /**
     * 首页-推荐列表
     */
    @GET("api/v5/index/tab/allRec")
    suspend fun getHomePageRecommend(): HomePageRecommend

    /**
     * 首页-日报列表
     */
    @GET("api/v5/index/tab/feed")
    suspend fun getDaily(): Daily

    /**
     * 社区-推荐列表
     */
    @GET("api/v7/community/tab/rec")
    suspend fun getCommunityRecommend(): CommunityRecommend

    /**
     * 社区-关注列表
     */
    @GET("api/v6/community/tab/follow")
    suspend fun gethFollow(): Follow

    /**
     * 通知-推送列表
     */
    @GET("api/v3/messages")
    suspend fun getPushMessage(): PushMessage

    /**
     * 搜索-热搜关键词
     */
    @GET("api/v3/queries/hot")
    suspend fun getHotSearch(): List<String>

}