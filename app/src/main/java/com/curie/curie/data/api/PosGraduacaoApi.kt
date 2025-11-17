package com.curie.curie.data.api

import com.curie.curie.data.model.AreaCarreira
import com.curie.curie.data.model.Graduacao
import com.curie.curie.data.model.PosGraduacao
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PosGraduacaoApi {
    @GET("posGraduacao")
    fun getAll(): Call<List<PosGraduacao>>

    @GET("posGraduacao/{id}")
    fun getById(@Path("id") id: Long): Call<PosGraduacao>
}