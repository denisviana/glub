package com.deniscosta.glub.core.di

import com.deniscosta.glub.core.repository.IOfferRepository
import com.deniscosta.glub.core.repository.OfferRepository
import org.koin.dsl.module

val repositoryModule = module {

   single<IOfferRepository> { OfferRepository(get()) }

}