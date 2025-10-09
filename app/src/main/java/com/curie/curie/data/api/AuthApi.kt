package com.curie.curie.data.api

import com.curie.curie.data.model.LoginRequest
import com.curie.curie.data.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}