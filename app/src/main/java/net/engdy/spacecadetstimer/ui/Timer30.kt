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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.engdy.spacecadetstimer.Phase
import net.engdy.spacecadetstimer.R

@Composable
fun Timer30(
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
            style = MaterialTheme.typography.displayLarge
        )
        Row {
            if (timerUIState.phase == Phase.JUMP_8) {
                Button(
                    shape = MaterialTheme.shapes.small,
                    onClick = {
                        timerViewModel.addDuration(30_000L)
                    },
                    modifier = Modifier.padding(end = 10.dp)
                ) {
                    Text(
                        stringResource(R.string.button_add_30),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
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
                enabled = !timerUIState.isEnded,
                shape = MaterialTheme.shapes.small,
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
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val imgId = when (timerUIState.phase) {
            Phase.ACTION_3 -> R.drawable.sc_step_3
            Phase.ACTION_3A -> R.drawable.sc_step_3a
            Phase.ACTION_3B -> R.drawable.sc_step_3b
            Phase.SCIENCE_1_2 -> R.drawable.sc_step_1_2
            Phase.TRACTOR_BEAM_5 -> R.drawable.sc_step_5
            Phase.FIRE_WEAPONS_6 -> R.drawable.sc_step_6
            Phase.JUMP_8 -> R.drawable.sc_step_8
            Phase.REPAIR_9 -> R.drawable.sc_step_9
            else -> 0
        }
        val cdescId = when (timerUIState.phase) {
            Phase.ACTION_3 -> R.string.cd_action_3
            Phase.ACTION_3A -> R.string.cd_action_3A
            Phase.ACTION_3B -> R.string.cd_action_3B
            Phase.SCIENCE_1_2 -> R.string.cd_science
            Phase.TRACTOR_BEAM_5 -> R.string.cd_tractor
            Phase.FIRE_WEAPONS_6 -> R.string.cd_fire_weapons
            Phase.JUMP_8 -> R.string.cd_jump
            Phase.REPAIR_9 -> R.string.cd_repair
            else -> 0
        }

        Image(
            painterResource(imgId),
            contentDescription = stringResource(cdescId),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth(0.75f)
                .padding(20.dp),
            contentScale = ContentScale.FillWidth
        )
    }
}