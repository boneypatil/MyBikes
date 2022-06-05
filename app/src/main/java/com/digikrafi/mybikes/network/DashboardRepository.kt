package com.digikrafi.mybikes.network
import com.digikrafi.mybikes.base.BaseRepository
import com.digikrafi.mybikes.base.DetectConnection
import com.digikrafi.mybikes.model.BikeDashboardResponse


/**
 * Created by rahul,p
 *
 */
class DashboardRepository(
    private val service: DashboardService,
    connectionUtil: DetectConnection
) : BaseRepository(connectionUtil) {

    suspend fun getDashboardContentRepo(): Result<BikeDashboardResponse> =
        executeForResponse(
            { it },
            { service.getDashboardContentAsync("pub_transport","stacje_rowerowe") }
        )
}