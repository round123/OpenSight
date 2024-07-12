package com.tao.opensight.ui.home.daily

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tao.opensight.http.MainPageApi
import com.tao.opensight.http.MainPageApi.Companion.DAILY_URL
import com.tao.opensight.model.Daily

class DailyPagingSource(private val mainPageService: MainPageApi) :
    PagingSource<String, Daily.Item>() {

    override fun getRefreshKey(state: PagingState<String, Daily.Item>): String? =null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Daily.Item> {
        return try {
            val page = params.key ?: DAILY_URL
            val repoResponse = mainPageService.getDaily(page)
            val repoItems = repoResponse.itemList
            val prevKey = null
            val nextKey =
                if (repoItems.isNotEmpty() && !repoResponse.nextPageUrl.isNullOrEmpty()) repoResponse.nextPageUrl else null
            LoadResult.Page(repoItems, prevKey, nextKey)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

}