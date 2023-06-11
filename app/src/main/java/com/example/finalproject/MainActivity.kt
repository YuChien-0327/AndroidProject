package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var btnStart: Button
    lateinit var btnSeeRecord: Button

    lateinit var etMyTeam: EditText
    lateinit var etOtherTeam: EditText

    companion object {
        val MyTeamName: String = "MyTeamName"
        val OtherTeamName: String = "OtherTeamName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etMyTeam = findViewById(R.id.et_myTeam)
        etOtherTeam = findViewById(R.id.et_otherTeam)

        btnStart = findViewById(R.id.btn_start)
        btnSeeRecord = findViewById(R.id.btn_seeRecord)

        btnStart.setOnClickListener { start() }
        btnSeeRecord.setOnClickListener { seeRecord() }
    }
    private fun start(){

        if(etMyTeam.text.toString().isEmpty()){
            Toast.makeText(this, "未輸入自己隊伍名稱，請再檢查一次!!!", Toast.LENGTH_LONG).show()
            return
        }else if(etOtherTeam.text.toString().isEmpty()){
            Toast.makeText(this, "未輸入對手隊伍名稱，請再檢查一次!!!", Toast.LENGTH_LONG).show()
            return
        }else{
            val intent = Intent()
            intent.setClass(this@MainActivity, Game::class.java)
            intent.putExtra(MyTeamName, etMyTeam.text.toString())
            intent.putExtra(OtherTeamName, etOtherTeam.text.toString())
            startActivity(intent)
        }

    }
    private fun seeRecord(){

    }
}
