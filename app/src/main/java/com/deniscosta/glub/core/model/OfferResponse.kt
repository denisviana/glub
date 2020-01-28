package com.deniscosta.glub.core.model

import com.google.gson.annotations.SerializedName

data class OfferResponse(
    @SerializedName("code")
    val code : Int,
    @SerializedName("response")
    val offers : List<Offer>
)