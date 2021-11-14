package com.alvaroestrada.zaragozamonuments.di

import com.alvaroestrada.zaragozamonuments.data.network.MonumentsApiClient
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit{
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl("https://www.zaragoza.es/sede/servicio/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideMonumentsApiClient(retrofit: Retrofit): MonumentsApiClient{
        return retrofit.create(MonumentsApiClient::class.java)
    }
}