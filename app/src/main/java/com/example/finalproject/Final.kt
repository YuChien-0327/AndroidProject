package com.example.finalproject

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text


class Final : AppCompatActivity() {
    lateinit var tvWin: TextView
    lateinit var tvFirst: TextView
    lateinit var tvSecond: TextView
    lateinit var tvThird: TextView
    lateinit var tvWinScore: TextView
    lateinit var tvLossScore: TextView
    lateinit var btnReturn: Button
    lateinit var btnFighter: Button
    lateinit var btnSave: Button
    lateinit var spChooseNum: Spinner

    private val options = ArrayList<Int>()
    private var players = ArrayList<Player>()
    private val winTypeArr = arrayOf("攻擊得分", "發球得分", "攔網得分", "對方失誤")
    private val lossTypeArr = arrayOf("攻擊失分", "發球失分", "攔網失分", "舉球失誤",  "防守失分", "接發失分", "觸網越界")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final)

        val intent = intent

        if (intent.hasExtra("players")) {
            val playersArray = intent.getSerializableExtra("players") as Array<Player>
            players = ArrayList<Player>(playersArray.toList())
        }

        tvWin = findViewById(R.id.tv_win)
        tvFirst = findViewById(R.id.tv_first)
        tvSecond = findViewById(R.id.tv_second)
        tvThird = findViewById(R.id.tv_third)

        btnReturn = findViewById(R.id.btn_return)
        btnFighter = findViewById(R.id.btn_fighter)
        btnSave = findViewById(R.id.btn_save)

        tvWin.text = "恭喜" + intent.getStringExtra("winner") + "獲得勝利!!!"

        var scores = intent.getSerializableExtra("scores") as Array<String>
        tvFirst.text = scores[0]
        tvSecond.text = scores[1]
        tvThird.text = scores[2]

        btnReturn.setOnClickListener { backToHome() }
        btnFighter.setOnClickListener { showAlertDialog()  }
        btnSave.setOnClickListener { save() }
    }
    private fun setOption(){
        for(player in players){
            options.add(player.number)
        }
    }
    private fun getWinScore(number: Int): String {
        for (player in players) {
            if (player.number == number) {
                var s: String = "\n得分\n\n"
                for (i in (0..3)) {
                    s += (winTypeArr[i] + "：")
                    s += (player.winTypesArr[i].toString() + "次\n")
                }
                s += "\n總共得分：" + player.win_score + "分"
                return s
            }
        }
        return ""
    }
    private fun getLossScore(number: Int): String {
        for (player in players) {
            if (player.number == number) {
                var s: String = "\n失分\n\n"
                for (i in (0..6)) {
                    s += (lossTypeArr[i] + "：")
                    s += (player.lossTypesArr[i].toString() + "次\n")
                }
                s += "\n總共得分：" + player.loss_score + "分"
                return s
            }
        }
        return ""
    }

    private fun showAlertDialog() {
        setOption()
        val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_popwindow, null)
        spChooseNum = dialogView.findViewById(R.id.sp_chooseNum)
        tvWinScore = dialogView.findViewById(R.id.tv_winScore)
        tvLossScore = dialogView.findViewById(R.id.tv_lossScore)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spChooseNum.adapter = adapter

        spChooseNum.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedOption = options[position]
                tvWinScore.text = getWinScore(selectedOption)
                tvLossScore.text = getLossScore(selectedOption)
                // 根據選擇的項目執行相應的操作
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 當沒有選擇項目時執行的操作
            }
        }


        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(dialogView)

        val alertDialog = alertDialogBuilder.create()
        alertDialog.window?.setLayout(600, 350)
        alertDialog.show()
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