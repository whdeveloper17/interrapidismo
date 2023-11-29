package com.wilsonhernandez.interrrapidisimo.data.repository

import android.util.Log
import com.wilsonhernandez.interrrapidisimo.data.network.ApiClient
import com.wilsonhernandez.interrrapidisimo.data.network.response.BoardResponse
import com.wilsonhernandez.interrrapidisimo.data.network.service.BoardService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class BoardApiRepository @Inject constructor(private val boardService: BoardService) {
    suspend fun getBoadrs(success: (boardResponse: List<BoardResponse>) -> Unit, failed: () -> Unit) {
        boardService.getData(success = success, failed = failed)
    }
}