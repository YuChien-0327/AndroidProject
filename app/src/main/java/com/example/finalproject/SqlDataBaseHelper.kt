package com.example.finalproject

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory

import android.database.sqlite.SQLiteOpenHelper


class SqlDataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "mytable.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        // 建立應用程式需要的表格
        val sql = "CREATE TABLE if not exists mytable(id integer PRIMARY KEY AUTOINCREMENT, title text NOT NULL, winner text NOT NULL)"
        db.execSQL(sql)
        //db.execSQL("CREATE TABLE IF NOT EXISTS mytable (id INTEGER PRIMARY KEY, name TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // 刪除原有的表格、並呼叫 onCreate 建立新表格
        db.execSQL("DROP TABLE IF EXISTS mytable")
        onCreate(db)
    }

    fun deleteTable() {
        val database = writableDatabase
        val dropTableQuery = "DROP TABLE IF EXISTS $DATABASE_NAME"
        database.execSQL(dropTableQuery)
        database.close()
    }
}