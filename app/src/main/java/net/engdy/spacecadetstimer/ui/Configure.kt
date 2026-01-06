package net.engdy.spacecadetstimer.ui

/**
 * Copyright (c) 2026 Andy Foulke. All rights reserved.
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.engdy.spacecadetstimer.Phase
import net.engdy.spacecadetstimer.R

@Composable
fun Configure(
    timerViewModel: TimerViewModel
) {
    val timerUIState: TimerUIState by timerViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.configuration),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.how_many_players),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(end = 10.dp)
            )
            val playerCount = timerUIState.playerCount
            Switch(
                checked = playerCount > 4,
                onCheckedChange = {
                    timerViewModel.setPlayerCount(if (playerCount > 4) 3 else 5)
                }
            )
            Text(
                text = if (playerCount > 4)
                    stringResource(R.string.button_5)
                else
                    stringResource(R.string.button_3_4),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
        Row(
            modifier = Modifier
                .padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.using_science),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(end = 10.dp)
            )
            val science = timerUIState.usingScience
            Switch(
                checked = science,
                onCheckedChange = {
                    timerViewModel.setUsingScience(!science)
                }
            )
            Text(
                text = if (science) stringResource(R.string.button_yes) else stringResource(R.string.button_no),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
        Row(
            modifier = Modifier
                .padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.play_background),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(end = 10.dp)
            )
            val background = timerUIState.isBackgroundPlaying
            Switch(
                checked = background,
                onCheckedChange = {
                    timerViewModel.setBackgroundPlaying(!background)
                }
            )
            Text(
                text = if (background) stringResource(R.string.button_yes) else stringResource(R.string.button_no),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
        Button(
            shape = MaterialTheme.shapes.small,
            onClick = {
                timerViewModel.setPhase(Phase.NEMESIS_START)
            }
        ) {
            Text(
                text = stringResource(R.string.button_next),
                style = MaterialTheme.typography.titleSmall
            )
        }
        Text(
            text = stringResource(R.string.science_expansion),
            style = MaterialTheme.typography.bodyMedium
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            shape = MaterialTheme.shapes.small,
            onClick = {
                timerViewModel.setPhase(Phase.README)
            },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(20.dp)
        ) {
            Text(
                text = stringResource(R.string.button_readme),
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}