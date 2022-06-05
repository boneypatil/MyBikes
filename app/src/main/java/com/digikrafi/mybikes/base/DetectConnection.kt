package com.digikrafi.mybikes.base
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import com.digikrafi.mybikes.network.ErrorResult
import com.digikrafi.mybikes.network.ErrorCodes
import java.net.SocketTimeoutException

/**
 * Created by rahul.p
 *
 */
object DetectConnection {

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkRequest: NetworkRequest

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun initialize(context: Context) {
        connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
    }


    /**
     * Method to check internet connectivity
     * Returns true if internet is available,
     * & false if internet not available
     */
    fun isInternetAvailable(): Boolean {
        var result = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_VPN) && actNw.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                ) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_VPN) && actNw.hasTransport(
                    NetworkCapabilities.TRANSPORT_CELLULAR
                ) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_VPN) && actNw.hasTransport(
                    NetworkCapabilities.TRANSPORT_ETHERNET
                ) -> true

                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }

    /**
     * Method to check internet connectivity issue
     * Returns false if internet is available,
     * & true if internet not available
     */
    fun internetConnectivityIssue() = !isInternetAvailable()

    fun <T> getErrorMessage(e: Exception? = null) =
        when {
            internetConnectivityIssue() ->
                ErrorResult(
                    ErrorCodes.ERROR_NO_CONNECTION,
                    "Please check your internet connection and try again"
                )
            e is SocketTimeoutException ->
                ErrorResult(
                    ErrorCodes.ERROR_TIMEOUT,
                    "Your request is taking too long to get a response. Please try again after some time."
                )
            else ->
                ErrorResult<T>(ErrorCodes.ERROR_UNKNOWN, "Something went wrong")
        }


}