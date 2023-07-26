package com.example.punnetverse.data

data class HomePageResponse(
    val pageNumber: Long,
    val pageSize: Long,
    val videos: List<Template?>,
    val totalElements: Long,
    val totalPages: Long,
    val lastPage: Boolean,
)
