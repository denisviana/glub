package com.deniscosta.glub.core.repository

import com.deniscosta.glub.core.model.Offer
import com.deniscosta.glub.core.model.OfferResponse
import retrofit2.Response

interface IOfferRepository {
    suspend fun getOffersFromRemote() : OfferResponse
}