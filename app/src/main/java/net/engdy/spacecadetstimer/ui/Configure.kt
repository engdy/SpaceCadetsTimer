package net.engdy.spacecadetstimer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
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
            fontSize = 12.em,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.how_many_players),
                fontSize = 6.em,
                color = Color.White,
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
                fontSize = 6.em,
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
        Row(
            modifier = Modifier
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.using_science),
                fontSize = 6.em,
                color = Color.White,
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
                fontSize = 6.em,
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
        Button(
            onClick = {
                timerViewModel.setPhase(Phase.NEMESIS_START)
            }
        ) {
            Text(text = stringResource(R.string.button_next))
        }
        Text(
            text = stringResource(R.string.science_expansion),
            fontSize = 3.em,
            color = Color.White
        )
    }
}