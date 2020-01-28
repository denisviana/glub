package com.deniscosta.glub.core.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OfferLocation(
    @SerializedName("lat")
    val latitude : Double,
    @SerializedName("lon")
    val longitude : Double
) : Parcelable