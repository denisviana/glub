package com.deniscosta.glub.core.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OfferPartner(
    @SerializedName("name")
    val name : String
) : Parcelable