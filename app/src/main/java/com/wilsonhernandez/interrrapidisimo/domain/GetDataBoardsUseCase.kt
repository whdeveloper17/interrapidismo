package com.wilsonhernandez.interrrapidisimo.domain

import com.wilsonhernandez.interrrapidisimo.data.network.response.BoardResponse
import com.wilsonhernandez.interrrapidisimo.data.repository.BoardApiRepository
import javax.inject.Inject

class GetDataBoardsUseCase @Inject constructor(private val repository: BoardApiRepository) {
    suspend fun invoke(success: (boardResponse: List<BoardResponse>) -> Unit, failed: () -> Unit) {
        repository.getBoadrs(success, failed)
    }
}