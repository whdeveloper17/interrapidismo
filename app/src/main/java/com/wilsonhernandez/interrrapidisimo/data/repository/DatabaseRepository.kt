package com.wilsonhernandez.interrrapidisimo.data.repository

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.wilsonhernandez.interrrapidisimo.data.database.DatabaseSqlite
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val databaseSqlite: DatabaseSqlite) {
    @RequiresApi(Build.VERSION_CODES.P)
    fun createBoard(name: String, sql: String, isCreate: (Boolean) -> Unit) {
        if (!databaseSqlite.isTableExists(name)) {
            databaseSqlite.createTable(sql)
            isCreate.invoke(true)
        } else {
            isCreate.invoke(false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun setBoard(name: String, pk: String, numberField: Int, dateUpdate: String) {
        databaseSqlite.insert(name, pk, numberField, dateUpdate)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun getListBoard(success: (List<String>) -> Unit) {
        success.invoke(databaseSqlite.selectAll())
    }
}