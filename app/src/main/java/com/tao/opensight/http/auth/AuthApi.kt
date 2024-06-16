package com.tao.opensight.http.auth

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi{
    //获取后续请求要用的信息
    @FormUrlEncoded
    @POST("v1/system/auth/token")
    suspend fun getToken(
        @Field("device_info") paramString1: String?,
        @Field("grant_type") paramString2: String?,
        @Field("sign") paramString3: String?,
        @Field("ts") paramString4: String?,
        @Field("refresh_token") paramString5: String?
    ): Any

    @FormUrlEncoded
    @POST("v1/system/auth/token")
    suspend fun getTokenSync(
        @Field("device_info") paramString1: String?,
        @Field("grant_type") paramString2: String?,
        @Field("sign") paramString3: String?,
        @Field("ts") paramString4: String?,
        @Field("refresh_token") paramString5: String?
    ): Any

    @FormUrlEncoded
    @POST("/v1/user/push/save_push_info")
    suspend fun savePushInfo(
        @Field("client_id") paramString1: String?,
        @Field("push_platform") paramString2: String?
    ): Any

    @POST("/v1/system/common/init")
    suspend fun init(): Response<String>
}