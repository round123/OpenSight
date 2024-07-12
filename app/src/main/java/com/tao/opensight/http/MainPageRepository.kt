package com.tao.opensight.http

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tao.opensight.model.Daily
import com.tao.opensight.ui.home.daily.DailyPagingSource
import kotlinx.coroutines.flow.Flow

object MainPageRepository {
    private const val PAGE_SIZE = 50
    val mainPageService = ApiManager.create(MainPageApi::class.java)

    fun getDailyPagingData(): Flow<PagingData<Daily.Item>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { DailyPagingSource(mainPageService) }
        ).flow
    }
}