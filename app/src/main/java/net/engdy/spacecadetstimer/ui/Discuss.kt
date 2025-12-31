package net.engdy.spacecadetstimer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import net.engdy.spacecadetstimer.R

@Composable
fun Discuss(
    timerViewModel: TimerViewModel
) {
    val timerUIState by timerViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = timerViewModel.secondsToString(timerUIState.secondsLeft),
            fontSize = 32.em,
            fontWeight = FontWeight.Bold,
            color = if (timerUIState.isCommBreakdown) Color(0xFFE25326) else Color.White
        )
        Row(

        ) {
            Button(
                onClick = {
                    timerViewModel.setCommBreakdown(!timerUIState.isCommBreakdown)
                }
            ) {
                Text(stringResource(R.string.button_comm_breakdown))
            }
            Button(
                onClick = {
                    timerViewModel.resetTimer()
                }
            ) {
                Text(stringResource(R.string.button_reset))
            }
            Button(
                enabled = !timerUIState.isEnded,
                onClick = {
                    timerViewModel.startPauseTimer()
                }
            ) {
                Text(if (timerUIState.isRunning)
                    stringResource(R.string.button_pause)
                else
                    stringResource(R.string.button_start))
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painterResource(R.drawable.sc_step_1),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth(0.75f)
                .padding(20.dp),
            contentScale = ContentScale.FillWidth
        )
    }
}

