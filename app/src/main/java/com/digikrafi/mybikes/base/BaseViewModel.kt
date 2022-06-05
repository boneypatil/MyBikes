package com.digikrafi.mybikes.base

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.digikrafi.mybikes.network.Success
import com.digikrafi.mybikes.network.ErrorResult
import com.digikrafi.mybikes.network.Result

/**
 * Created by rahul.p
 *
 */

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val error = MutableLiveData<ErrorResult<*>>()
    val state = MutableLiveData<BaseState>()
    fun <T> handleResult(
        result: Result<T>
    ) = when (result) {
        is Success -> {
            state.postValue(BaseState.Success)
            true
        }
        is ErrorResult -> {
            error.postValue(result)
            state.postValue(BaseState.Error)
            false
        }
    }

    enum class BaseState {
        Error, Loading, Success
    }

    fun log(message: String) = Log.d(this.javaClass.simpleName, message)
}