package com.curie.curie.data.api

import com.curie.curie.data.model.Tarefa
import com.curie.curie.data.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {

    @GET("user/{id}")
    fun getById(@Path("id") id: Long): Call<User>

    @PUT("update/{id}")
    fun update(@Path("id") id: Long?, @Body user: User): Call<User>

    @DELETE("delete/{id}")
    fun delete(@Path("id") id: Long?): Call<User>

}