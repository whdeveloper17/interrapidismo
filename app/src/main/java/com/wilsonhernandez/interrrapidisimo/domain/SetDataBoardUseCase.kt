package com.wilsonhernandez.interrrapidisimo.domain

import android.os.Build
import androidx.annotation.RequiresApi
import com.wilsonhernandez.interrrapidisimo.data.repository.DatabaseRepository
import javax.inject.Inject

class SetDataBoardUseCase @Inject constructor(private val databaseRepository: DatabaseRepository) {
    @RequiresApi(Build.VERSION_CODES.P)
     fun invoke(name:String,pk:String,numberField:Int,dateUpdate:String) {
        databaseRepository.setBoard(name, pk, numberField, dateUpdate)
    }

}