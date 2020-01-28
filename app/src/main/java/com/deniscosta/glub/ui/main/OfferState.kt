package com.deniscosta.glub.ui.main

import com.deniscosta.glub.core.model.Offer

open class OfferState

class LoadingOffers : OfferState()

class OfferLoadingSuccessful(
    val offers : List<Offer>
) : OfferState()

class OfferLoadingFailed(
    val message : String
) : OfferState()

class ShowOfferDetails(val offer : Offer) : OfferState()
class LocationPermissions : OfferState()