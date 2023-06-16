package com.example.finalproject

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class Recoder : AppCompatActivity() {

    lateinit var tvId: TextView
    lateinit var tvTitle: TextView
    lateinit var tvWinner: TextView

    lateinit var btnReturn: Button

    private lateinit var databaseHelper: SqlDataBaseHelper
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recoder)

        tvId = findViewById(R.id.tv_id)
        tvTitle = findViewById(R.id.tv_title)
        tvWinner = findViewById(R.id.tv_winner)
        btnReturn = findViewById(R.id.btn_return)

        btnReturn.setOnClickListener { back() }
        loadRecode()
    }

    private fun back(){
        val intent = Intent()
        intent.setClass(this@Recoder, MainActivity::class.java)
        startActivity(intent)
    }

    fun loadRecode() {
        databaseHelper = SqlDataBaseHelper(this)
        database = databaseHelper.writableDatabase
        // 执行查询操作
        val selectQuery = "SELECT * FROM mytable"
        val cursor: Cursor = database.rawQuery(selectQuery, null)

        var idString = "編號：\n"
        var titleString = "比賽隊伍：\n"
        var winnerString = "贏家：\n"
        if (cursor.moveToFirst()) {
            do {
                idString += cursor.getInt(cursor.getColumnIndex("id")).toString() + "\n"
                titleString += cursor.getString(cursor.getColumnIndex("title")) + "\n"
                winnerString += cursor.getString(cursor.getColumnIndex("winner")) + "\n"
            } while (cursor.moveToNext())
        }
        tvId.text = idString
        tvTitle.text = titleString
        tvWinner.text = winnerString

        cursor.close()
        database.close()
    }
}