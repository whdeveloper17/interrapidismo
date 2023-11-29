package com.wilsonhernandez.interrrapidisimo.data.network

import com.wilsonhernandez.interrrapidisimo.data.network.response.BoardResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiClient {
    @GET("SincronizadorDatos/ObtenerEsquema/true/")
    fun getData(@Header("usuario") value:String): Call<List<BoardResponse>>
}