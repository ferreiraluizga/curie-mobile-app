package com.curie.curie.data.api

import com.curie.curie.data.model.TipoComportamento
import com.curie.curie.data.model.TipoTemperamento
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TipoTemperamentoApi {
    @GET("tipoTemperamento")
    fun getAll(): Call<List<TipoTemperamento>>

    @GET("tipoTemperamento/{id}")
    fun getById(@Path("id") id: Long): Call<TipoTemperamento>
}
