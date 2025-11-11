package com.curie.curie.data.api

import com.curie.curie.data.model.Perfil
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PerfilApi {
    @POST("perfil/save")
    fun save(@Body perfil: Perfil): Call<Perfil>

    @GET("perfil/{id}")
    fun getById(@Path("id") id: Long): Call<Perfil>

    @GET("perfil/usuario/{id}/mais-recente")
    fun getMaisRecente(@Path("id") id: Long): Call<Perfil>

    @PUT("perfil/{id}")
    fun update(@Path("id") id: Long, @Body perfil: Perfil): Call<Perfil>

    @DELETE("perfil/delete/{id}")
    fun delete(@Path("id") id: Long?): Call<Void>
}