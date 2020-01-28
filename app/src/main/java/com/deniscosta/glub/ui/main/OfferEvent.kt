package com.deniscosta.glub.ui.main

import android.location.Location
import com.deniscosta.glub.core.model.Offer
import com.google.android.gms.maps.model.LatLng

open class OfferEvent

class RefreshOfferList(
    val location : Location
) : OfferEvent()

class OnClickOffer(val offer : Offer) : OfferEvent()
class CheckLocationPermissions : OfferEvent()
