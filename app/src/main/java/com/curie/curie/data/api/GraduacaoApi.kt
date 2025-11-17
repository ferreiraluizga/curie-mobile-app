package com.curie.curie.data.api

import com.curie.curie.data.model.AreaCarreira
import com.curie.curie.data.model.Graduacao
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GraduacaoApi {
    @GET("graduacao")
    fun getAll(): Call<List<Graduacao>>

    @GET("graduacao/{id}")
    fun getById(@Path("id") id: Long): Call<Graduacao>
}