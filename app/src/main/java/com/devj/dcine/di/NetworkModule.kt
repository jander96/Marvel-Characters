package com.devj.dcine.di

import com.devj.dcine.data.ApiKeyInterceptor
import com.devj.dcine.data.MarvelApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://gateway.marvel.com:443/v1/"
    private const val PUBLIC_API_KEY = "946fb94e96a9ff6af66528cddf159b38"
    private const val PRIVATE_API_KEY = "28c32cdaeea818ccbc8de23aaeb1f393c03140bb"

    @Provides
    fun provideMovieApi(retrofit: Retrofit): MarvelApi {
        return retrofit.create(MarvelApi::class.java)
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()


    @Provides
    fun provideOkHttpClient(apiKeyInterceptor: ApiKeyInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .build()

    @Provides
    fun provideInterceptor(): ApiKeyInterceptor = ApiKeyInterceptor(privateApiKey = PRIVATE_API_KEY, publicApiKey = PUBLIC_API_KEY)
}