package com.digikrafi.mybikes.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.digikrafi.mybikes.base.BaseViewModel
import com.digikrafi.mybikes.model.BikeDashboardResponse
import com.digikrafi.mybikes.network.DashboardRepository
import kotlinx.coroutines.launch

/**
 * Created by rahul.p
 *
 */
class DashboardViewModel(
    val repo: DashboardRepository,
    application: Application
) : BaseViewModel(application) {

    private val dashboardContentLiveData = MutableLiveData<BikeDashboardResponse>()



    fun getDashboardContent(): LiveData<BikeDashboardResponse> = dashboardContentLiveData



    fun fetchDashBoardContent() {
        state.postValue(BaseState.Loading)
        viewModelScope.launch {
            val result = repo.getDashboardContentRepo()
            val dashboardContent = result.get()
            val success = handleResult(result)
            if (success) {
                dashboardContent?.let { content ->
                    dashboardContentLiveData.postValue(content)

                }
            }
        }
    }



}