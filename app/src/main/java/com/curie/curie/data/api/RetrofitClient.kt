package com.curie.curie.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private const val TOKEN = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJmYXN0bWFya2V0Iiwic3ViIjoiMSIsImV4cCI6MTc1OTUwNTUyNiwiaWF0IjoxNzU5NTA1MjI2LCJzY29wZSI6ImFkbWluIn0.BJat5Ew7Gse0wYlrok_TmMnigKRgtGozL1LHCiWk7idcTC45vSzqGkFdOaEI2SytO3dpc8cKT3yGxTG3EXBXgWvlWEocIRZ-ACzSkv4y-XOzdqydqULuVTj4dUZrPFO0OLVFSAzc0eBTR7deFCpmVa9CqSzpgiebvZ8PxpouUWLwM4OKAhmzJfB9zVHhzXmVLlpzUP4JKLDUtoTAc5UHxOAEIL_D2Kt3rcL66GT5SDFxBvMfcXv2ER9VFzttV5iuVBbVKmdx8Ygh4xdimfSdilMmgYcTbpaKBG2EEz8W9qI_2KLA_zjHgpGXeOIXIJz7df4pBuTEyFyU2BSebFTuEA"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(TOKEN))
        .build()

    val tarefaApi: TarefaApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TarefaApi::class.java)
    }
}