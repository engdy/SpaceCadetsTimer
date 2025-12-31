package net.engdy.spacecadetstimer

import android.os.CountDownTimer
import android.util.Log

class SHGTimer(
    var duration: Long,
    val interval: Long,
    val tick: (Long) -> Unit = { Log.d(TAG, "tick") },
    val finish: () -> Unit = { Log.d(TAG, "finished") }
) {
    private var timer: CountDownTimer? = null
    private var currentDuration = duration

    fun start() {
        timer = createTimer()
        timer?.start()
    }

    fun cancel() {
        timer?.cancel()
        timer = null
    }

    fun reset() {
        timer?.cancel()
        timer = null
        currentDuration = duration
    }

    fun updateDuration(duration: Long) {
        this.duration = duration
        reset()
    }


    private fun createTimer(): CountDownTimer {
        return object: CountDownTimer(currentDuration, interval) {
            override fun onFinish() {
                finish()
            }

            override fun onTick(millisUntilFinished: Long) {
                currentDuration = millisUntilFinished
                tick(millisUntilFinished)
            }
        }
    }

    companion object {
        val TAG = SHGTimer::class.simpleName
    }
}