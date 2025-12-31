package net.engdy.spacecadetstimer.ui

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import net.engdy.spacecadetstimer.Phase
import net.engdy.spacecadetstimer.SHGTimer

class TimerViewModel(
    duration: Long,
    val finalTickingDuration: Long = TEN_SECONDS_IN_MILLIS,
    val context: Context
) : ViewModel() {
    private var originalDuration = duration
    private val _uiState = MutableStateFlow(TimerUIState(secondsLeft = (duration / 1_000L).toInt()))
    val uiState: StateFlow<TimerUIState> = _uiState.asStateFlow()
    private val timer: SHGTimer = SHGTimer(
        duration = duration,
        interval = 500L,
        tick = { millis ->
            onTick(millis)
        },
        finish = {
            onFinish()
        }
    )
    private var _tickingExoPlayer: ExoPlayer = ExoPlayer.Builder(context).build()
    val tickingExoPlayer: ExoPlayer
        get() = _tickingExoPlayer

    private var _buzzerExoPlayer: ExoPlayer = ExoPlayer.Builder(context).build()
    val buzzerExoPlayer: ExoPlayer
        get() = _buzzerExoPlayer

    init {
        val tickingUri = Uri.parse("android.resource://net.engdy.spacecadetstimer/raw/ticking")
        val tickingMediaItem: MediaItem = MediaItem.fromUri(tickingUri)
        tickingExoPlayer.setMediaItem(tickingMediaItem)
        tickingExoPlayer.repeatMode = ExoPlayer.REPEAT_MODE_ONE
        tickingExoPlayer.prepare()

        val buzzerUri = Uri.parse("android.resource://net.engdy.spacecadetstimer/raw/buzzer")
        val buzzerMediaItem: MediaItem = MediaItem.fromUri(buzzerUri)
        buzzerExoPlayer.setMediaItem(buzzerMediaItem)
        buzzerExoPlayer.repeatMode = ExoPlayer.REPEAT_MODE_OFF
        buzzerExoPlayer.prepare()
    }

    private fun resetSounds() {
        tickingExoPlayer.stop()
        buzzerExoPlayer.stop()
    }
    fun onTick(millis: Long) {
        Log.d(TAG, "onTick($millis)")
        val secondsLeft = (millis / 1_000L).toInt()
        _uiState.update { currentState ->
            currentState.copy(
                secondsLeft = secondsLeft
            )
        }
        if (!uiState.value.isFinalTicking && millis < finalTickingDuration) {
            _uiState.update { currentState ->
                currentState.copy(
                    isFinalTicking = true
                )
            }
            tickingExoPlayer.seekTo(0)
            tickingExoPlayer.prepare()
            tickingExoPlayer.play()
        }
    }

    fun onFinish() {
        Log.d(TAG, "onFinish()")
        _uiState.update { currentState ->
            currentState.copy(
                isRunning = false,
                isFinalTicking = false,
                isEnded = true,
                secondsLeft = 0
            )
        }
        buzzerExoPlayer.seekTo(0)
        buzzerExoPlayer.prepare()
        buzzerExoPlayer.play()
        tickingExoPlayer.stop()
    }

    fun resetTimer() {
        Log.d(TAG, "resetTimer()")
        timer.reset()
        _uiState.update { currentState ->
            currentState.copy(
                isRunning = false,
                isEnded = false,
                isFinalTicking = false,
                secondsLeft = (originalDuration / 1_000L).toInt()
            )
        }
        resetSounds()
    }

    fun startPauseTimer() {
        if (uiState.value.isRunning) {
            _uiState.update { currentState ->
                currentState.copy(
                    isRunning = false
                )
            }
            timer.cancel()
            resetSounds()
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    isRunning = true
                )
            }
            timer.start()
            if (uiState.value.isFinalTicking) {
                tickingExoPlayer.seekTo(0)
                tickingExoPlayer.prepare()
                tickingExoPlayer.play()
            }
        }
    }

    fun setPlayerCount(count: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                playerCount = count
            )
        }
    }

    fun startOver() {
        _uiState.update { currentState ->
            currentState.copy(
                phase = Phase.CONFIG,
                isRunning = false,
                isEnded = false,
                isFinalTicking = false
            )
        }
    }

    fun setUsingScience(usingScience: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                usingScience = usingScience
            )
        }
    }

    fun setPhase(phase: Phase) {
        originalDuration = phase.duration
        if (phase == Phase.DISCUSS_1 && uiState.value.isCommBreakdown) {
            originalDuration -= ONE_MINUTE_IN_MILLIS
        }
        timer.updateDuration(originalDuration)
        _uiState.update { currentState ->
            currentState.copy(
                phase = phase,
                secondsLeft = (originalDuration / 1_000L).toInt(),
            )
        }
    }

    fun nextPhase() {
        when (uiState.value.phase) {
            Phase.DISCUSS_1 -> {
                if (uiState.value.usingScience)
                    setPhase(Phase.SCIENCE_1_2)
                else
                    setPhase(Phase.PREPARE_2)
            }
            Phase.SCIENCE_1_2 -> {
                setPhase(Phase.PREPARE_2)
            }
            Phase.PREPARE_2 -> {
                if (uiState.value.playerCount > 3)
                    setPhase(Phase.ACTION_3)
                else
                    setPhase(Phase.ACTION_3A)
            }
            Phase.ACTION_3, Phase.ACTION_3B -> {
                setPhase(Phase.RESOLUTION_4)
            }
            Phase.ACTION_3A -> {
                setPhase(Phase.ACTION_3B)
            }
            Phase.RESOLUTION_4 -> {
                setPhase(Phase.TRACTOR_BEAM_5)
            }
            Phase.TRACTOR_BEAM_5 -> {
                setPhase(Phase.FIRE_WEAPONS_6)
            }
            Phase.FIRE_WEAPONS_6 -> {
                setPhase(Phase.ENEMY_NEMESIS_7)
            }
            Phase.ENEMY_NEMESIS_7 -> {
                setPhase(Phase.JUMP_8)
            }
            Phase.JUMP_8 -> {
                setPhase(Phase.REPAIR_9)
            }
            Phase.REPAIR_9 -> {
                setPhase(Phase.DISCUSS_1)
            }
            else -> {
                Log.d(TAG, "Unknown phase: ${uiState.value.phase}")
            }
        }
    }

    fun prevPhase() {
        when (uiState.value.phase) {
            Phase.DISCUSS_1 -> {
                setPhase(Phase.REPAIR_9)
            }
            Phase.SCIENCE_1_2 -> {
                setPhase(Phase.DISCUSS_1)
            }
            Phase.PREPARE_2 -> {
                if (uiState.value.usingScience)
                    setPhase(Phase.SCIENCE_1_2)
                else
                    setPhase(Phase.DISCUSS_1)
            }
            Phase.ACTION_3, Phase.ACTION_3A -> {
                setPhase(Phase.PREPARE_2)
            }
            Phase.ACTION_3B -> {
                setPhase(Phase.ACTION_3A)
            }
            Phase.RESOLUTION_4 -> {
                if (uiState.value.playerCount > 3)
                    setPhase(Phase.ACTION_3)
                else
                    setPhase(Phase.ACTION_3B)
            }
            Phase.TRACTOR_BEAM_5 -> {
                setPhase(Phase.RESOLUTION_4)
            }
            Phase.FIRE_WEAPONS_6 -> {
                setPhase(Phase.TRACTOR_BEAM_5)
            }
            Phase.ENEMY_NEMESIS_7 -> {
                setPhase(Phase.FIRE_WEAPONS_6)
            }
            Phase.JUMP_8 -> {
                setPhase(Phase.ENEMY_NEMESIS_7)
            }
            Phase.REPAIR_9 -> {
                setPhase(Phase.JUMP_8)
            }
            else -> {
                Log.d(TAG, "Unknown phase: ${uiState.value.phase}")
            }
        }
    }

    fun setNemesis(nemesis: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                nemesis = nemesis
            )
        }
    }

    fun setCommBreakdown(breakdown: Boolean) {
        originalDuration = uiState.value.phase.duration
        if (uiState.value.phase == Phase.DISCUSS_1 && breakdown) {
            originalDuration -= ONE_MINUTE_IN_MILLIS
        }
        timer.updateDuration(originalDuration)
        timer.reset()
        resetSounds()
        _uiState.update { currentState ->
            currentState.copy(
                isRunning = false,
                isEnded = false,
                isFinalTicking = false,
                isCommBreakdown = breakdown,
                secondsLeft = (originalDuration / 1_000L).toInt()
            )
        }
    }

    fun secondsToString(seconds: Int): String {
        val mins = seconds / 60
        val tens = (seconds % 60) / 10
        val secs = seconds % 10
        return "${mins}:${tens}${secs}"
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        tickingExoPlayer.release()
        buzzerExoPlayer.release()
    }

    companion object {
        val TAG = TimerViewModel::class.simpleName
        const val TEN_SECONDS_IN_MILLIS = 10_000L
        const val ONE_MINUTE_IN_MILLIS = 60_000L
    }
}