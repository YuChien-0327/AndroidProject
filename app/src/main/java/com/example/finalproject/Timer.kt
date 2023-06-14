package com.example.finalproject
import android.os.Handler

class Timer {
    private var isRunning: Boolean = false
    private var isPaused: Boolean = false
    private var startTime: Long = 0
    private var elapsedTime: Long = 0
    private var pausedTime: Long = 0
    private val handler: Handler = Handler()
    private val timerRunnable: Runnable = object : Runnable {
        override fun run() {
            val currentMilliseconds = System.currentTimeMillis()
            elapsedTime = currentMilliseconds - startTime
            handler.postDelayed(this, 1000) // 更新計時器每秒一次

            // 呼叫計時器回調函數，傳遞更新的時間給 UI
            timerCallback?.onTimerTick(elapsedTime)
        }
    }
    private var timerCallback: TimerCallback? = null

    interface TimerCallback {
        fun onTimerTick(elapsedTime: Long)
    }

    fun startTimer(callback: TimerCallback) {
        if (!isRunning) {
            isRunning = true
            if (!isPaused) {
                startTime = System.currentTimeMillis()
            } else {
                startTime = System.currentTimeMillis() - pausedTime
            }
            timerCallback = callback
            handler.postDelayed(timerRunnable, 0)
            isPaused = false
        }
    }

    fun stopTimer() {
        if (isRunning && !isPaused) {
            isRunning = false
            pausedTime = elapsedTime
            timerCallback = null
            handler.removeCallbacks(timerRunnable)
            isPaused = true
        }
    }
}
