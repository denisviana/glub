package com.deniscosta.glub.core.api

import com.deniscosta.glub.core.model.Offer
import com.deniscosta.glub.core.model.OfferResponse
import retrofit2.Response
import retrofit2.http.GET

interface RestApiService {

    @GET("deals.json")
    suspend fun getOffers(): OfferResponse
}