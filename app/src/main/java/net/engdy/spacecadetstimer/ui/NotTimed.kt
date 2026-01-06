package net.engdy.spacecadetstimer.ui

/**
 * Copyright (c) 2026 Andy Foulke. All rights reserved.
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.engdy.spacecadetstimer.Phase
import net.engdy.spacecadetstimer.R

@Composable
fun NotTimed(
    timerViewModel: TimerViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            Text(
                text = stringResource(R.string.not_timed),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            Button(
                shape = MaterialTheme.shapes.small,
                onClick = {
                    timerViewModel.nextPhase()
                }
            ) {
                Text(
                    stringResource(R.string.button_next),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val currentPhase = timerViewModel.uiState.collectAsState().value.phase

        Image(
            painterResource(if (currentPhase == Phase.PREPARE_2)
                R.drawable.sc_step_2
            else
                R.drawable.sc_step_4),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth(0.75f)
                .padding(20.dp),
            contentScale = ContentScale.FillWidth
        )
    }
}