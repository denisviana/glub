package com.deniscosta.glub.core.repository

import com.deniscosta.glub.core.api.RestApiService
import com.deniscosta.glub.core.model.Offer
import com.deniscosta.glub.core.model.OfferResponse
import retrofit2.Response

class OfferRepository(
    val api : RestApiService
) : IOfferRepository {

    override suspend fun getOffersFromRemote() : OfferResponse = api.getOffers()

}