package com.devj.dcine.data

import retrofit2.http.GET
import retrofit2.http.Query


interface MarvelApi {
    @GET("public/characters")
    suspend fun getMarvelCharacters( @Query("limit") limit: Int, @Query("offset") offset: Int): MarvelResponse

}