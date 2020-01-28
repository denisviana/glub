package com.deniscosta.glub.core.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Offer(
    @SerializedName("partner")
    val partner : OfferPartner,
    @SerializedName("sale_price")
    val price : Double,
    @SerializedName("images")
    val images : List<OfferImage>,
    @SerializedName("short_title")
    val description : String,
    @SerializedName("location")
    val location : List<OfferLocation>
) : Parcelable