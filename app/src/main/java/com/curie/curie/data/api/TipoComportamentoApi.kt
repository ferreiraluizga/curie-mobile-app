package com.curie.curie.data.api

import com.curie.curie.data.model.TipoComportamento
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TipoComportamentoApi {
    @GET("tipoComportamento")
    fun getAll(): Call<List<TipoComportamento>>

    @GET("tipoComportamento/{id}")
    fun getById(@Path("id") id: Long): Call<TipoComportamento>
}
