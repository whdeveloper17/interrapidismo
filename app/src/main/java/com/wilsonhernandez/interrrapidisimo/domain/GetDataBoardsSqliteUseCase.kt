package com.wilsonhernandez.interrrapidisimo.domain

import android.os.Build
import androidx.annotation.RequiresApi
import com.wilsonhernandez.interrrapidisimo.data.repository.DatabaseRepository
import javax.inject.Inject

class GetDataBoardsSqliteUseCase @Inject constructor(private val databaseRepository: DatabaseRepository) {

    @RequiresApi(Build.VERSION_CODES.P)
    suspend fun invoke(success:(List<String>)->Unit){
        databaseRepository.getListBoard(success)
    }
}