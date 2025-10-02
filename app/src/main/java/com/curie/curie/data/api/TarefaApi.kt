package com.curie.curie.data.api

import com.curie.curie.data.model.Tarefa
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TarefaApi {

    @POST("tarefas/save")
    fun save(@Body tarefa: Tarefa): Call<Tarefa>

    @GET("tarefas/{id}")
    fun getById(@Path("id") id: Long): Call<Tarefa>

    @GET("tarefas/usuario/{userId}")
    fun getByUsuario(@Path("userId") userId: Long): Call<List<Tarefa>>

    @GET("tarefas/usuario/{userId}/buscar-por-nome")
    fun getByNome(
        @Path("userId") userId: Long,
        @Query("nome") nome: String
    ): Call<List<Tarefa>>

    @GET("tarefas/usuario/{userId}/ordenar-por-prazo")
    fun getByPrazo(@Path("userId") userId: Long): Call<List<Tarefa>>

    @GET("tarefas/usuario/{userId}/ordenar-por-prioridade")
    fun getByPrioridade(@Path("userId") userId: Long): Call<List<Tarefa>>

    @PUT("tarefas/update/{id}")
    fun update(@Path("id") id: Long, @Body tarefa: Tarefa): Call<Tarefa>

    @DELETE("tarefas/delete/{id}")
    fun delete(@Path("id") id: Long): Call<Void>
}