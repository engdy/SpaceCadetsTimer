package net.engdy.spacecadetstimer.ui

import net.engdy.spacecadetstimer.Phase

data class TimerUIState(
    val secondsLeft: Int = 300,
    val isRunning: Boolean = false,
    val isEnded: Boolean = false,
    val isFinalTicking: Boolean = false,
    val isBackgroundPlaying: Boolean = false,
    val isCommBreakdown: Boolean = false,
    val playerCount: Int = 3,
    val usingScience: Boolean = false,
    val nemesis: Int = -1,
    val phase: Phase = Phase.CONFIG
)