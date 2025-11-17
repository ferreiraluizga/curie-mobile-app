package com.curie.curie.data.api

import com.curie.curie.data.model.AreaCarreira
import com.curie.curie.data.model.Graduacao
import com.curie.curie.data.model.Profissao
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfissaoApi {
    @GET("profissao")
    fun getAll(): Call<List<Profissao>>

    @GET("profissao/{id}")
    fun getById(@Path("id") id: Long): Call<Profissao>
}