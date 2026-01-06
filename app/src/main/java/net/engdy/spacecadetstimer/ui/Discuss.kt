package net.engdy.spacecadetstimer.ui

/**
 * Copyright (c) 2026 Andy Foulke. All rights reserved.
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import net.engdy.spacecadetstimer.R

@Composable
fun Discuss(
    timerViewModel: TimerViewModel
) {
    val timerUIState by timerViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = timerViewModel.secondsToString(timerUIState.secondsLeft),
            style = MaterialTheme.typography.displayLarge,
            color = if (timerUIState.isCommBreakdown) Color(0xFFE25326) else Color.White
        )
        Row {
            Button(
                shape = MaterialTheme.shapes.small,
                onClick = {
                    timerViewModel.setCommBreakdown(!timerUIState.isCommBreakdown)
                },
                modifier = Modifier.padding(end = 10.dp)
            ) {
                Text(
                    stringResource(R.string.button_comm_breakdown),
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Button(
                shape = MaterialTheme.shapes.small,
                onClick = {
                    timerViewModel.resetTimer()
                },
                modifier = Modifier.padding(end = 10.dp)
            ) {
                Text(
                    stringResource(R.string.button_reset),
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Button(
                shape = MaterialTheme.shapes.small,
                enabled = !timerUIState.isEnded,
                onClick = {
                    timerViewModel.startPauseTimer()
                },
                modifier = Modifier.padding(end = 10.dp)
            ) {
                Text(
                    if (timerUIState.isRunning)
                        stringResource(R.string.button_pause)
                    else
                        stringResource(R.string.button_start),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
        Spacer(modifier = Modifier.height(80.dp))
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painterResource(R.drawable.sc_step_1),
            contentDescription = stringResource(R.string.cd_discuss),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth(0.75f)
                .padding(20.dp),
            contentScale = ContentScale.FillWidth
        )
    }
}
