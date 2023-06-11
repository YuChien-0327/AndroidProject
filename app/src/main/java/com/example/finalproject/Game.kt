package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class Game : AppCompatActivity() {
    lateinit var tvTime: TextView
    lateinit var tvGameNum: TextView
    lateinit var tvMyTeamName: TextView
    lateinit var tvOtherTeamName: TextView
    lateinit var etWinNumber: EditText
    lateinit var etLossNumber: EditText
    lateinit var btnBack: Button
    lateinit var btnStop: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        tvTime = findViewById(R.id.tv_time)
        tvGameNum = findViewById(R.id.tv_gameNum)
        tvMyTeamName = findViewById(R.id.tv_myTeamName)
        tvOtherTeamName = findViewById(R.id.tv_otherTeamName)
        etWinNumber = findViewById(R.id.et_winNumber)
        etLossNumber = findViewById(R.id.et_lossNumber)
        btnBack = findViewById(R.id.btn_back)
        btnStop = findViewById(R.id.btn_stop)


        tvMyTeamName.text = intent.getStringExtra(MainActivity.MyTeamName)
        tvOtherTeamName.text = intent.getStringExtra(MainActivity.OtherTeamName)

        btnBack.setOnClickListener { back() }
        btnStop.setOnClickListener { stop() }
    }
    private fun back(){
        AlertDialog.Builder(this)
            .setMessage("尚未完成計分，確定要返回嗎?")
            .setPositiveButton("確定") { _, _ ->
                Toast.makeText(applicationContext, "Yess", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("取消") { _, _ ->
                Toast.makeText(applicationContext, "Noo", Toast.LENGTH_SHORT).show()
            }
            .show()
    }
    private fun stop(){

    }
}