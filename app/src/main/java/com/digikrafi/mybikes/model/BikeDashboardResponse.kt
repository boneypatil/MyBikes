package com.digikrafi.mybikes.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BikeDashboardResponse (

	val features : List<Features>,
	val crs : Crs,
	val type : String
):Parcelable