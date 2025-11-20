package com.curie.curie.data.api

import com.curie.curie.data.model.Carreira
import com.curie.curie.data.model.Tarefa
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CarreiraApi {

    @POST("carreira/save")
    fun save(@Body carreira: Carreira): Call<Carreira>

    @GET("carreira/{id}")
    fun getById(@Path("id") id: Long): Call<Carreira>

    @DELETE("carreira/delete/{id}")
    fun delete(@Path("id") id: Long?): Call<Void>
}