package net.engdy.spacecadetstimer

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import net.engdy.spacecadetstimer.ui.Configure
import net.engdy.spacecadetstimer.ui.Discuss
import net.engdy.spacecadetstimer.ui.Nemesis
import net.engdy.spacecadetstimer.ui.NotTimed
import net.engdy.spacecadetstimer.ui.Timer30
import net.engdy.spacecadetstimer.ui.TimerViewModel
import net.engdy.spacecadetstimer.ui.Tutorials
import net.engdy.spacecadetstimer.ui.theme.SpaceCadetsTimerTheme

class SpaceCadetsActivity : ComponentActivity() {
    private lateinit var timerViewModel: TimerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate()")
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        timerViewModel = TimerViewModel(
            duration = FIVE_MINUTES_IN_MILLIS,
            finalTickingDuration = TEN_SECONDS_IN_MILLIS,
            this
        )

        setContent {
            SpaceCadetsTimerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SpaceCadets(
                        modifier = Modifier.padding(innerPadding),
                        timerViewModel = timerViewModel
                    )
                }
            }
        }
    }

    companion object {
        val TAG = SpaceCadetsActivity::class.simpleName
        private const val FIVE_MINUTES_IN_MILLIS = 300_000L
        private const val TEN_SECONDS_IN_MILLIS = 10_000L
    }
}

@Composable
fun SpaceCadets(
    modifier: Modifier = Modifier,
    timerViewModel: TimerViewModel
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowInsetsControllerCompat(window, view).apply {
                hide(WindowInsetsCompat.Type.statusBars()) // Hide the status bar
                // Optional: control behavior on swipe
                systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }
    val timerUiState by timerViewModel.uiState.collectAsState()
    val imgId: Int = when (timerUiState.nemesis) {
        0 -> R.drawable.spacecadetsn2
        1 -> R.drawable.spacecadetsn1
        2 -> R.drawable.spacecadets0
        3 -> R.drawable.spacecadets1
        4 -> R.drawable.spacecadets2
        5 -> R.drawable.spacecadets3
        6 -> R.drawable.spacecadets4
        7 -> R.drawable.spacecadets5
        8 -> R.drawable.spacecadets6
        else -> R.drawable.spacecadets
    }

    Box(modifier = modifier
        .fillMaxHeight(0.5f)
        .aspectRatio(1f)
        .padding(20.dp)
        .background(Color.White)
        .padding(1.dp)
    ) {
        Image(
            painterResource(imgId),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop,
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        val buttonsEnabled = timerUiState.phase != Phase.CONFIG && timerUiState.phase != Phase.NEMESIS_START
        Button(
            enabled = buttonsEnabled,
            onClick = {
                timerViewModel.startOver()
            }
        ) {
            Text(stringResource(R.string.button_restart))
        }
        Button(
            enabled = buttonsEnabled,
            onClick = {
                timerViewModel.prevPhase()
            }
        ) {
            Text(stringResource(R.string.button_prev))
        }
        Button(
            enabled = buttonsEnabled,
            onClick = {
                timerViewModel.nextPhase()
            }
        ) {
            Text(stringResource(R.string.button_next))
        }
    }
    when (timerUiState.phase) {
        Phase.CONFIG -> {
            Configure(timerViewModel)
        }
        Phase.NEMESIS_START,
        Phase.ENEMY_NEMESIS_7 -> {
            Nemesis(timerViewModel)
        }
        Phase.TUTORIALS -> {
            Tutorials(timerViewModel)
        }
        Phase.DISCUSS_1 -> {
            Discuss(timerViewModel)
        }
        Phase.PREPARE_2,
        Phase.RESOLUTION_4 -> {
            NotTimed(timerViewModel)
        }
        Phase.SCIENCE_1_2,
        Phase.ACTION_3,
        Phase.ACTION_3A,
        Phase.ACTION_3B,
        Phase.TRACTOR_BEAM_5,
        Phase.FIRE_WEAPONS_6,
        Phase.JUMP_8,
        Phase.REPAIR_9 -> {
            Timer30(timerViewModel)
        }
    }
}
