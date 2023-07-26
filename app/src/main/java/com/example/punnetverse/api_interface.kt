package com.example.punnetverse

import retrofit2.Call
import retrofit2.http.GET

interface api_interface {
    @GET("/gimme")
    fun getdata(): Call<responsedata>
}