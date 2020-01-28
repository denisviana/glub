package com.deniscosta.glub.core.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OfferImage(
    @SerializedName("image")
    val image : String,
    @SerializedName("thumb")
    val thumb : String,
    @SerializedName("original")
    val original : String
) : Parcelable