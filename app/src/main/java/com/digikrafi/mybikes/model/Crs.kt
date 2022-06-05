package com.digikrafi.mybikes.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Crs(

	val type: String?,
	val properties: Properties?
):Parcelable