package com.digikrafi.mybikes.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Properties(
	val bikes: String?,
	val bike_racks: String?,
	val label: String?,
	val free_racks: String?,
	val updated: String?
	):Parcelable