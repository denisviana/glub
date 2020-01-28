package com.deniscosta.glub.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniscosta.glub.core.model.Offer
import com.deniscosta.glub.core.repository.OfferRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenViewModel : ViewModel() {

    val state = MutableLiveData<SplashScreenState>()

    init {
        state.value = AppInitiated()
        cooldown()
    }


    private fun cooldown(){

        state.value = Waiting()

        viewModelScope.launch {
            delay(5000)
            state.value = RedirectToHome()
        }

    }


}