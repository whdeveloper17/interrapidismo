package com.wilsonhernandez.interrrapidisimo.data.network.service

import android.util.Log
import com.wilsonhernandez.interrrapidisimo.data.network.ApiClient
import com.wilsonhernandez.interrrapidisimo.data.network.response.BoardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class BoardService @Inject constructor(private val apiClient: ApiClient) {

    suspend fun getData(success: (boardResponse: List<BoardResponse>) -> Unit, failed: () -> Unit) {
        withContext(Dispatchers.IO) {
            apiClient.getData("admin").enqueue(object : Callback<List<BoardResponse>> {
                override fun onResponse(
                    call: Call<List<BoardResponse>>,
                    response: Response<List<BoardResponse>>
                ) {
                    if (!response.body().isNullOrEmpty()) {
                        response.body()?.let { success.invoke(it) }
                    }
                }

                override fun onFailure(call: Call<List<BoardResponse>>, t: Throwable) {
                    failed.invoke()
                }

            })
        }
    }
}