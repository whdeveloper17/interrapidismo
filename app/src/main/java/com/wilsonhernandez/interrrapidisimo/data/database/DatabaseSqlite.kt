package com.wilsonhernandez.interrrapidisimo.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi


@RequiresApi(Build.VERSION_CODES.P)
class DatabaseSqlite(
    context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    private  val TABLE_NAME = "mi_tabla"
    private  val COLUMN_NAME = "name"
    private  val COLUMN_PK = "pk"
    private  val COLUMN_NUMBER_FIELD = "numberField"
    private  val COLUMN_DATE_UPDATE = "dateUpdate"
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableSQL = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_PK TEXT," +
                "$COLUMN_NUMBER_FIELD INTEGER," +
                "$COLUMN_DATE_UPDATE TEXT);"

        db?.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun isTableExists(tableName: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT name FROM sqlite_master WHERE type='table' AND name=?", arrayOf(tableName)
        )
        var tableExists = false
        if (cursor != null) {
            tableExists = cursor.count > 0
            cursor.close()
        }
        return tableExists
    }

    fun createTable( sql: String) {

        writableDatabase.execSQL(sql)
    }

    fun insert(name: String, pk: String, numberField: Int, dateUpdate: String){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_PK, pk)
            put(COLUMN_NUMBER_FIELD, numberField)
            put(COLUMN_DATE_UPDATE, dateUpdate)
        }

        db.insert(TABLE_NAME, null, values)

        db.close()
    }

    fun selectAll(): List<String> {
        val db = readableDatabase
        val projection = arrayOf(
            COLUMN_NAME
        )

        val cursor = db.query(
            TABLE_NAME,
            projection,
            null, // selection
            null, // selectionArgs
            null, // groupBy
            null, // having
            null  // orderBy
        )

        val dataList = mutableListOf<String>()

        with(cursor) {
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow(COLUMN_NAME))
                val data = name
                dataList.add(data)
            }
        }

        cursor.close()
        //db.close()

        return dataList
    }
}