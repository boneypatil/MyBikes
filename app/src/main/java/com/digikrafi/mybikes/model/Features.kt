package com.digikrafi.mybikes.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Features(

	val geometry: Geometry?,
	val id: Int,
	val type: String?,
	val properties: Properties?
):Parcelable