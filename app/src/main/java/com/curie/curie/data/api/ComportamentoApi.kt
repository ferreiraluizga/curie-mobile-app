package com.curie.curie.data.api

import com.curie.curie.data.model.Comportamento
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ComportamentoApi {
    @POST("comportamento/save")
    fun save(@Body comportamento: Comportamento): Call<Comportamento>

    @GET("comportamento/{id}")
    fun getById(@Path("id") id: Long): Call<Comportamento>

    @DELETE("comportamento/delete/{id}")
    fun delete(@Path("id") id: Long?): Call<Void>
}