package com.deniscosta.glub.ui.main

import android.location.Location
import android.location.LocationManager.GPS_PROVIDER
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniscosta.glub.core.model.Offer
import com.deniscosta.glub.core.model.OfferResponse
import com.deniscosta.glub.core.repository.IOfferRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class OfferListViewModel(
    private val offerRepository: IOfferRepository
) : ViewModel() {

    val state = MutableLiveData<OfferState>()

    fun event(event : OfferEvent){

        when(event){
            is RefreshOfferList -> {
                getOffers(event.location)
            }
            is OnClickOffer -> {
                state.value = ShowOfferDetails(event.offer)
            }
            is CheckLocationPermissions -> {
                state.value = LocationPermissions()
            }
        }

    }

    private fun getOffers(location: Location){

        state.value = LoadingOffers()

        viewModelScope.launch {

            val response : OfferResponse = try{
                offerRepository.getOffersFromRemote()
            }catch (e : HttpException){
                e.printStackTrace()
                state.postValue(OfferLoadingFailed(message = e.message()))
                return@launch
            }catch (e: Throwable){
                e.printStackTrace()
                state.postValue(OfferLoadingFailed(message = "Ooops: Something else went wrong"))
                return@launch
            }

            state.postValue(OfferLoadingSuccessful(
                offers = filterOffersByLocation(
                    location, response.offers
                ))
            )
        }

    }

    private fun filterOffersByLocation(userLocation : Location, offers : List<Offer>) : List<Offer>{

        val filteredOffers = mutableListOf<Offer>()

        offers.forEach {

            if(it.location.isNotEmpty()){
                val offerLocation = Location(GPS_PROVIDER).apply {
                    latitude = it.location.first().latitude
                    longitude = it.location.first().longitude
                }

                if(userLocation.distanceTo(offerLocation) <= 300000)
                    filteredOffers.add(it)
            }

        }

        return filteredOffers
    }


}