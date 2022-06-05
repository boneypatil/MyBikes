package com.digikrafi.mybikes.network

import com.digikrafi.mybikes.model.BikeDashboardResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.*

/**
 * Created by rahul.p
 * Api service calls
 */
interface DashboardService {
    @GET("/mim/plan/map_service.html")
    fun getDashboardContentAsync( @Query(value = "mtype") mtype: String, @Query(value = "co") co: String,): Deferred<BikeDashboardResponse>
}