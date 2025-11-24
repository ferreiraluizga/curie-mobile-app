package com.curie.curie.data.api

import com.curie.curie.data.model.Carreira
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CarreiraApi {

    @POST("carreira/save")
    fun save(@Body carreira: Carreira): Call<Carreira>

    @GET("carreira/{id}")
    fun getById(@Path("id") id: Long): Call<Carreira>

    @DELETE("carreira/delete/{id}")
    fun delete(@Path("id") id: Long?): Call<Void>

    @GET("carreira/usuario/{userId}")
    fun getByUsuario(@Path("userId") userId: Long): Call<Carreira>
}