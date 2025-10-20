package com.curie.curie.data.api

import com.curie.curie.data.model.Meta
import com.curie.curie.data.model.Tarefa
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MetaApi {

    @POST("metas/save")
    fun save(@Body meta: Meta): Call<Meta>

    @GET("metas/{id}")
    fun getById(@Path("id") id: Long): Call<Meta>

    @GET("metas/usuario/{userId}")
    fun getByUsuario(@Path("userId") userId: Long): Call<List<Meta>>

    @GET("metas/usuario/{userId}/buscar-por-nome")
    fun getByNome(
        @Path("userId") userId: Long,
        @Query("nome") nome: String
    ): Call<List<Meta>>

    @GET("metas/usuario/{userId}/ordenar-por-prazo")
    fun getByPrazo(@Path("userId") userId: Long): Call<List<Meta>>

    @GET("metas/usuario/{userId}/ordenar-por-prioridade")
    fun getByPrioridade(@Path("userId") userId: Long): Call<List<Meta>>

    @PUT("metas/update/{id}")
    fun update(@Path("id") id: Long?, @Body meta: Meta): Call<Meta>

    @DELETE("metas/delete/{id}")
    fun delete(@Path("id") id: Long?): Call<Void>
}