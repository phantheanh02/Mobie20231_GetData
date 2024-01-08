package com.anhpt.myapplication

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("users.json")
    fun getUsers(): Call<List<User>>
}

