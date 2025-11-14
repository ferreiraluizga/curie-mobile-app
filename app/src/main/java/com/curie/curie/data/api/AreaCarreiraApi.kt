package com.curie.curie.data.api

import com.curie.curie.data.model.AreaCarreira
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AreaCarreiraApi {
    @GET("areaCarreira")
    fun getAll(): Call<List<AreaCarreira>>

    @GET("areaCarreira/{id}")
    fun getById(@Path("id") id: Long): Call<AreaCarreira>
}