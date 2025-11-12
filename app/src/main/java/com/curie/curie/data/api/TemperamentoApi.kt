package com.curie.curie.data.api

import com.curie.curie.data.model.Comportamento
import com.curie.curie.data.model.Temperamento
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TemperamentoApi {
    @POST("temperamento/save")
    fun save(@Body temperamento: Temperamento): Call<Temperamento>

    @GET("temperamento/{id}")
    fun getById(@Path("id") id: Long): Call<Temperamento>

    @DELETE("temperamento/delete/{id}")
    fun delete(@Path("id") id: Long?): Call<Void>
}