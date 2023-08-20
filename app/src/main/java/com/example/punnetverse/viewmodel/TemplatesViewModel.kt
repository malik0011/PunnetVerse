package com.example.punnetverse.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.punnetverse.api.MemeApiService
import com.example.punnetverse.data.HomePageResponse
import com.example.punnetverse.data.Template
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TemplatesViewModel: ViewModel() {

    var tempList = MutableLiveData<ArrayList<Template>>()

    fun getMemeTemp(pageNumber: Int) {
        MemeApiService.memesInstance.getHomeMemeTemp(pageNumber, 5).enqueue(object : Callback<HomePageResponse> {
            override fun onResponse(call: Call<HomePageResponse>, response: Response<HomePageResponse>) {
                tempList.postValue(response.body()?.videos as ArrayList<Template>?)
            }

            override fun onFailure(call: Call<HomePageResponse>, t: Throwable) {
                tempList.postValue(arrayListOf())
            }
        })
    }
    fun getTrendingMemeTemp(pageNumber: Int) {
        MemeApiService.memesInstance.getHomeMemeTemp(pageNumber, 5).enqueue(object : Callback<HomePageResponse> {
            override fun onResponse(call: Call<HomePageResponse>, response: Response<HomePageResponse>) {
                tempList.postValue(response.body()?.videos as ArrayList<Template>?)
            }

            override fun onFailure(call: Call<HomePageResponse>, t: Throwable) {
                tempList.postValue(arrayListOf())
            }
        })
    }

    fun getSearchTemp(caption: String) {
        MemeApiService.memesInstance.getSearchTemplates(caption, 0, 10).enqueue(object : Callback<HomePageResponse> {
            override fun onResponse(call: Call<HomePageResponse>, response: Response<HomePageResponse>) {
                tempList.postValue(response.body()?.videos as ArrayList<Template>?)
            }

            override fun onFailure(call: Call<HomePageResponse>, t: Throwable) {
                tempList.postValue(arrayListOf())
            }
        })
    }
}