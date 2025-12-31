package net.engdy.spacecadetstimer.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
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
                onClick = {
                    timerViewModel.setPhase(Phase.TUTORIALS)
                }
            ) {
                Text(text = stringResource(R.string.view_tutorials))
            }
            Text(
                text = stringResource(R.string.select_starting_point),
                fontSize = 6.em,
                color = Color.White
            )
        } else {
            Text(
                text = stringResource(R.string.update_nemesis),
                fontSize = 6.em,
                color = Color.White
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
        Image(
            painterResource(imageId),
            contentDescription = null,
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
                onClick = {
                    timerViewModel.setPhase(Phase.DISCUSS_1)
                },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Text(stringResource(R.string.button_next))
            }
        }
    }
}
