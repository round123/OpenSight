package com.tao.opensight.ui.home.daily

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tao.opensight.http.MainPageRepository
import com.tao.opensight.model.Daily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DailyViewmodel : ViewModel() {

    fun getPagingData(): Flow<PagingData<Daily.Item>> {
        return MainPageRepository.getDailyPagingData().cachedIn(viewModelScope)
    }
}