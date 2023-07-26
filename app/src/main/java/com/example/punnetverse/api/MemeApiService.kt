package com.example.punnetverse.api

import com.example.punnetverse.data.HomePageResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//base url
const val BASE_URL = "http://puneetverse.eu-north-1.elasticbeanstalk.com/puneetverse/"

interface MemeApiInterface {

    @GET("memes") //home page api
    fun getHomeMemeTemp() : Call<HomePageResponse>

    @GET("search") //search api
    fun getSearchTemplates(
        @Query("caption") caption: String,
        @Query("pageNumber") pageNumber: Int,
        @Query("pageSize") pageSize: Int): Call<HomePageResponse>
}

object MemeApiService {
    val memesInstance: MemeApiInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        memesInstance = retrofit.create(MemeApiInterface::class.java)
    }
}