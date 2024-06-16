package com.tao.opensight.http

import com.tao.opensight.model.Daily
import com.tao.opensight.model.Follow
import retrofit2.http.GET
import retrofit2.http.Url

interface MainPageService {
    /**
     * 首页-发现列表
     */
//    @GET
//    suspend fun getDiscovery(@Url url: String): Discovery

    /**
     * 首页-推荐列表
     */
//    @GET
//    suspend fun getHomePageRecommend(@Url url: String): HomePageRecommend

    /**
     * 首页-日报列表
     */
    @GET("api/v5/index/tab/feed")
    suspend fun getDaily(): Daily

    /**
     * 社区-推荐列表
     */
//    @GET
//    suspend fun getCommunityRecommend(@Url url: String): CommunityRecommend

    /**
     * 社区-关注列表
     */
    @GET
    suspend fun gethFollow(@Url url: String): Follow

    /**
     * 通知-推送列表
     */
//    @GET
//    suspend fun getPushMessage(@Url url: String): PushMessage

    /**
     * 搜索-热搜关键词
     */
    @GET("api/v3/queries/hot")
    suspend fun getHotSearch(): List<String>

}