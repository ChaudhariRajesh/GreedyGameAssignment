package com.example.greedygameassignment.di

import com.example.greedygameassignment.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Dependency injection class for Hilt

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule{

    private const val baseUrl = "http://ws.audioscrobbler.com/2.0/";

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApiService(retrofit: Retrofit) : ApiService
    {
        return retrofit.create(ApiService::class.java)
    }
}