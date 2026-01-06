package net.engdy.spacecadetstimer.ui

/**
 * Copyright (c) 2026 Andy Foulke. All rights reserved.
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.engdy.spacecadetstimer.Phase
import net.engdy.spacecadetstimer.R

@Composable
fun Nemesis(
    timerViewModel: TimerViewModel
) {
    val timerUIState by timerViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (timerUIState.phase == Phase.NEMESIS_START) {
            Button(
                shape = MaterialTheme.shapes.small,
                onClick = {
                    timerViewModel.setPhase(Phase.TUTORIALS)
                }
            ) {
                Text(
                    text = stringResource(R.string.view_tutorials),
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.select_starting_point),
                style = MaterialTheme.typography.titleMedium
            )
        } else {
            Text(
                text = stringResource(R.string.update_nemesis),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val imageId = when (timerUIState.nemesis) {
            0 -> R.drawable.sc_step_7a
            1 -> R.drawable.sc_step_7b
            2 -> R.drawable.sc_step_7c
            3 -> R.drawable.sc_step_7d
            4 -> R.drawable.sc_step_7e
            5 -> R.drawable.sc_step_7f
            6 -> R.drawable.sc_step_7g
            7 -> R.drawable.sc_step_7h
            8 -> R.drawable.sc_step_7i
            else -> R.drawable.sc_step_7
        }
        val contentDescId = when (timerUIState.nemesis) {
            0 -> R.string.cd_nemesis_neg2
            1 -> R.string.cd_nemesis_neg1
            2 -> R.string.cd_nemesis_0
            3 -> R.string.cd_nemesis_1
            4 -> R.string.cd_nemesis_2
            5 -> R.string.cd_nemesis_3
            6 -> R.string.cd_nemesis_4
            7 -> R.string.cd_nemesis_5
            8 -> R.string.cd_nemesis_6
            else -> R.string.cd_nemesis_not_set
        }

        Image(
            painterResource(imageId),
            contentDescription = stringResource(contentDescId),
            modifier = Modifier.align(Alignment.BottomStart)
                .fillMaxWidth(0.75f)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { offset ->
                            val numbutton: Int = (10.0 * offset.x / size.width).toInt()
                            Log.d("SCA", "numbutton($numbutton)")
                            if (numbutton > 0) {
                                timerViewModel.setNemesis(numbutton - 1)
                            }
                        }
                    )
                }
                .padding(20.dp),
            contentScale = ContentScale.FillWidth,
        )
        if (timerUIState.nemesis >= 0) {
            Button(
                shape = MaterialTheme.shapes.small,
                onClick = {
                    timerViewModel.setPhase(Phase.DISCUSS_1)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp)
            ) {
                Text(
                    stringResource(R.string.button_next),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}
