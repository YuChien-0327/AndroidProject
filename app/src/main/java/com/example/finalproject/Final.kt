package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import org.w3c.dom.Text

class Final : AppCompatActivity() {
    lateinit var tvWin: TextView
    lateinit var tvFirst: TextView
    lateinit var tvSecond: TextView
    lateinit var tvThird: TextView
    lateinit var btnReturn: Button
    lateinit var btnFighter: Button
    lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final)

        tvWin = findViewById(R.id.tv_win)
        tvFirst = findViewById(R.id.tv_first)
        tvSecond = findViewById(R.id.tv_second)
        tvThird = findViewById(R.id.tv_third)
        btnReturn = findViewById(R.id.btn_return)
        btnFighter = findViewById(R.id.btn_fighter)
        btnSave = findViewById(R.id.btn_save)

        tvWin.text = "恭喜" + intent.getStringExtra(Game.Winner) + "獲得勝利!!!"

        btnReturn.setOnClickListener { backToHome() }
        btnFighter.setOnClickListener {  }
        btnSave.setOnClickListener { save() }
    }

    private fun backToHome(){
        AlertDialog.Builder(this)
            .setMessage("保存了嗎?記得保存在離開歐!!!")
            .setPositiveButton("裡開") { _, _ ->
                val intent = Intent()
                intent.setClass(this@Final, MainActivity::class.java)
                startActivity(intent)
            }
            .setNegativeButton("取消") { _, _ ->
                Toast.makeText(applicationContext, "Noo", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun save(){

    }
}