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

    @POST("carreiras/save")
    fun save(@Body carreira: Carreira): Call<Carreira>

    @GET("carreiras/{id}")
    fun getById(@Path("id") id: Long): Call<Carreira>

    @DELETE("carreiras/delete/{id}")
    fun delete(@Path("id") id: Long?): Call<Void>
}