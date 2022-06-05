package com.digikrafi.mybikes.base


import androidx.fragment.app.Fragment
import com.digikrafi.mybikes.model.Features
import com.digikrafi.mybikes.network.ErrorResult
import com.digikrafi.mybikes.utils.toast


abstract class BaseDashboardFragment : Fragment() {

    private var dashboardListener: DashboardListener? = null


    protected fun onFeaturesSelected(features: Features) {
        dashboardListener?.onFeaturesSelected(features)
    }

    fun setListener(listener: DashboardListener) {
        this.dashboardListener = listener
    }

    interface DashboardListener {
        fun onFeaturesSelected(features: Features)
    }

    protected fun handleError(errorResult: ErrorResult<*>) {
        val message = errorResult.errorMessage
        if (message.isNotEmpty())
            context.toast(message)
    }

}