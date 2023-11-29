package com.wilsonhernandez.interrrapidisimo.domain

import android.os.Build
import androidx.annotation.RequiresApi
import com.wilsonhernandez.interrrapidisimo.data.repository.DatabaseRepository
import javax.inject.Inject

class CreateBoardSqliteUseCase @Inject constructor(private val databaseRepository: DatabaseRepository) {

    @RequiresApi(Build.VERSION_CODES.P)
    suspend fun invoke(name:String,sql:String,isCreate:(Boolean)->Unit){
        databaseRepository.createBoard(name,sql,isCreate)
    }
}