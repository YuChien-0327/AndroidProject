package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class Game : AppCompatActivity(), Timer.TimerCallback {
    private var gameNum: Int = 1 
    private var myScore: Int = 0
    private var otherScore: Int = 0
    var gameRecord = intArrayOf(0, 0, 0)

    private var isPlaying: Int = 1
    private lateinit var timer: Timer

    private var scores = arrayOf("", "", "提早結束比賽!!")
    private var players = ArrayList<Player>()

    lateinit var tvTime: TextView
    lateinit var tvGameNum: TextView
    lateinit var tvMyTeamName: TextView
    lateinit var tvOtherTeamName: TextView
    lateinit var tvMyTeamScore: TextView
    lateinit var tvOtherTeamScore: TextView
    lateinit var etWinNumber: EditText
    lateinit var etLossNumber: EditText
    lateinit var btnBack: Button
    lateinit var btnStop: Button
    lateinit var btnMyTeamGetPoint: Button
    lateinit var btnMyTeamLossPoint: Button
    lateinit var btnOtherTeamGetNumber: Button
    lateinit var btnOtherTeamLossNumber: Button

    lateinit var rgWin: RadioGroup
    lateinit var rbWin1: RadioButton
    lateinit var rbWin2: RadioButton
    lateinit var rbWin3: RadioButton
    lateinit var rbWin4: RadioButton

    lateinit var rgLoss: RadioGroup
    lateinit var rbLoss1: RadioButton
    lateinit var rbLoss2: RadioButton
    lateinit var rbLoss3: RadioButton
    lateinit var rbLoss4: RadioButton
    lateinit var rbLoss5: RadioButton
    lateinit var rbLoss6: RadioButton
    lateinit var rbLoss7: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        tvTime = findViewById(R.id.tv_time)
        tvGameNum = findViewById(R.id.tv_gameNum)
        tvMyTeamName = findViewById(R.id.tv_myTeamName)
        tvOtherTeamName = findViewById(R.id.tv_otherTeamName)
        tvMyTeamScore = findViewById(R.id.tv_myTeamScore)
        tvOtherTeamScore = findViewById(R.id.tv_otherTeamScore)
        etWinNumber = findViewById(R.id.et_winNumber)
        etLossNumber = findViewById(R.id.et_lossNumber)
        btnBack = findViewById(R.id.btn_back)
        btnStop = findViewById(R.id.btn_stop)
        btnMyTeamGetPoint = findViewById(R.id.btn_myTeamGetPoint)
        btnMyTeamLossPoint = findViewById(R.id.btn_myTeamLossPoint)
        btnOtherTeamGetNumber = findViewById(R.id.btn_otherTeamGetPoint)
        btnOtherTeamLossNumber = findViewById(R.id.btn_otherTeamLossPoint)
        rgWin = findViewById(R.id.rg_win)
        rbWin1 = findViewById(R.id.rb_win1)
        rbWin2 = findViewById(R.id.rb_win2)
        rbWin3 = findViewById(R.id.rb_win3)
        rbWin4 = findViewById(R.id.rb_win4)
        rgLoss = findViewById(R.id.rg_loss)
        rbLoss1 = findViewById(R.id.rb_loss1)
        rbLoss2 = findViewById(R.id.rb_loss2)
        rbLoss3 = findViewById(R.id.rb_loss3)
        rbLoss4 = findViewById(R.id.rb_loss4)
        rbLoss5 = findViewById(R.id.rb_loss5)
        rbLoss6 = findViewById(R.id.rb_loss6)
        rbLoss7 = findViewById(R.id.rb_loss7)

        timer = Timer()
        timer.startTimer(this)

        tvGameNum.text = "目前局數： " + gameNum
        tvMyTeamScore.text = myScore.toString()
        tvOtherTeamScore.text = otherScore.toString()

        tvMyTeamName.text = intent.getStringExtra(MainActivity.MyTeamName)
        tvOtherTeamName.text = intent.getStringExtra(MainActivity.OtherTeamName)

        btnBack.setOnClickListener { back() }
        btnStop.setOnClickListener { stop() }

        btnMyTeamGetPoint.setOnClickListener { myTeamGetPoint() }
        btnMyTeamLossPoint.setOnClickListener { myTeamLossPoint() }
        btnOtherTeamGetNumber.setOnClickListener { otherTeamGetPoint() }
        btnOtherTeamLossNumber.setOnClickListener { otherTeamLossPoint() }

    }
    override fun onDestroy() {
        super.onDestroy()
        timer.stopTimer()
    }
    override fun onTimerTick(elapsedTime: Long) {
        // 在這裡更新 UI，顯示計時器的時間
        val seconds = elapsedTime / 1000
        var hour = seconds / 3600
        var min = (seconds%3600) / 60
        var sec = (seconds % 3600) % 60

        tvTime.text = "目前時間： " + hour.toString() + ":" + min.toString() + ":" + sec.toString()
    }
    private fun checkIsHave(number: Int) {
        for (player in players) {
            if(number == player.number){
                return
            }
        }
        players.add(Player(number))
    }
    private fun addPlayerScore(number: String, winLoss: Int, style: Int) { //1Win
        checkIsHave(number.toInt())
        for (player in players) {
            if(number.toInt() == player.number){
                if(winLoss == 1){
                    player.win_score += 1
                    player.winTypesArr[style-1] += 1
                }else if(winLoss == 2){
                    player.loss_score += 1
                    player.lossTypesArr[style-1] += 1
                }

            }
        }
    }
    private fun newGame(){
        myScore = 0
        otherScore = 0
        gameNum += 1
        tvGameNum.text = "目前局數： " + gameNum
        tvMyTeamScore.text = myScore.toString()
        tvOtherTeamScore.text = otherScore.toString()
    }
    private fun finish(winner: String){
        val title = tvMyTeamName.text.toString() + " vs " + tvOtherTeamName.text.toString()
        val playersArray = players.toTypedArray()
        val intent = Intent(this, Final::class.java)
        intent.putExtra("winner", winner)
        intent.putExtra("players", playersArray)
        intent.putExtra("scores", scores)
        intent.putExtra("title", title)
        startActivity(intent)
    }
    private fun numOfWin(who: Int): Int{
        var time: Int = 0
        for(i in 0..2){
            if(gameRecord[i] == who){
                time += 1
            }
        }
        return time
    }
    private fun isWin(){
        if(gameNum == 3){ //第三局
            if(myScore == 15){
                scores[gameNum - 1] = "15:$otherScore"
                gameRecord[gameNum - 1] = 1
                finish(tvMyTeamName.text.toString())
            }else if(otherScore == 15){
                scores[gameNum - 1] = "$myScore:15"
                gameRecord[gameNum - 1] = 2
                finish(tvOtherTeamName.text.toString())
            }
        }else{ //1、2局
            if(myScore == 25){
                scores[gameNum - 1] = "25:$otherScore"
                gameRecord[gameNum - 1] = 1
                if(numOfWin(1) == 2){
                    finish(tvMyTeamName.text.toString())
                }else{
                    newGame()
                }
            }else if(otherScore == 25){
                scores[gameNum - 1] = "$myScore:25"
                gameRecord[gameNum - 1] = 2
                if(numOfWin(2) == 2){
                    finish(tvOtherTeamName.text.toString())
                }else{
                    newGame()
                }
            }else{
                return
            }
        }

    }
    private fun init(){
        etWinNumber.text.clear()
        etLossNumber.text.clear()
    }
    private fun getWinReason(): Int{
        var winReasonIdx: Int = 0
        when (rgWin.checkedRadioButtonId) {
            R.id.rb_win1 -> winReasonIdx = 1
            R.id.rb_win2 -> winReasonIdx = 2
            R.id.rb_win3 -> winReasonIdx = 3
            R.id.rb_win4 -> winReasonIdx = 4
            else -> winReasonIdx = 0
        }
        return winReasonIdx
    }
    private fun getLossReason(): Int{
        var lossReasonIdx: Int = 0
        when (rgLoss.checkedRadioButtonId) {
            R.id.rb_loss1 -> lossReasonIdx = 1
            R.id.rb_loss2 -> lossReasonIdx = 2
            R.id.rb_loss3 -> lossReasonIdx = 3
            R.id.rb_loss4 -> lossReasonIdx = 4
            R.id.rb_loss5 -> lossReasonIdx = 5
            R.id.rb_loss6 -> lossReasonIdx = 6
            R.id.rb_loss7 -> lossReasonIdx = 7
            else -> lossReasonIdx = 0
        }
        return lossReasonIdx
    }
    private fun myTeamGetPoint(){
        if(etWinNumber.text.toString().isEmpty()){
            Toast.makeText(this, "沒有寫是誰得分!!!", Toast.LENGTH_LONG).show()
        }else if(getWinReason() == 0){
            Toast.makeText(this, "沒有選得分原因!!!", Toast.LENGTH_LONG).show()
        }else{
            myScore += 1
            tvMyTeamScore.text = myScore.toString()
            addPlayerScore(etWinNumber.text.toString(), 1, getWinReason())
            init()
            isWin()
        }
    }
    private fun myTeamLossPoint(){
        myScore -= 1
        if(myScore < 0){
            myScore = 0
            Toast.makeText(this, "分數已經為0，不可再減下去!!!", Toast.LENGTH_LONG).show()
        }else{
            tvMyTeamScore.text = myScore.toString()
        }
    }
    private fun otherTeamGetPoint(){
        if(etLossNumber.text.toString().isEmpty()){
            Toast.makeText(this, "沒有寫是誰失分!!!", Toast.LENGTH_LONG).show()
        }else if(getLossReason() == 0){
            Toast.makeText(this, "沒有選失分原因!!!", Toast.LENGTH_LONG).show()
        }else{
            otherScore += 1
            tvOtherTeamScore.text = otherScore.toString()
            addPlayerScore(etLossNumber.text.toString(), 2, getLossReason())
            init()
            isWin()
        }
    }
    private fun otherTeamLossPoint(){
        otherScore -= 1
        if(otherScore < 0){
            otherScore = 0
            Toast.makeText(this, "分數已經為0，不可再減下去!!!", Toast.LENGTH_LONG).show()
        }else{
            tvOtherTeamScore.text = otherScore.toString()
        }
    }
    private fun back(){
        AlertDialog.Builder(this)
            .setMessage("尚未完成計分，確定要返回嗎?")
            .setPositiveButton("確定") { _, _ ->
                val intent = Intent()
                intent.setClass(this@Game, MainActivity::class.java)
                startActivity(intent)
            }
            .setNegativeButton("取消") { _, _ ->
                Toast.makeText(applicationContext, "Noo", Toast.LENGTH_SHORT).show()
            }
            .show()
    }
    private fun stop(){
        if(isPlaying == 1){
            isPlaying = 0
            btnStop.text = "開始"
            timer.stopTimer()
        }else{
            isPlaying = 1
            btnStop.text = "暫停"
            timer.startTimer(this)
        }
    }
}