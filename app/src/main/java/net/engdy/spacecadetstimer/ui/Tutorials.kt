package net.engdy.spacecadetstimer.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.media3.common.util.Log
import net.engdy.spacecadetstimer.Phase
import net.engdy.spacecadetstimer.R

data class TutorialItem(
    val iconId: Int,
    val titleId: Int,
    val youtube: String
)
val tutorialList = listOf(
    TutorialItem(
        R.drawable.sc_captain,
        R.string.tut_captain,
        "wBtvuFGmeOY"
    ),
    TutorialItem(
        R.drawable.sc_engineering,
        R.string.tut_engineering,
        "MreFAKMoJHI"
    ),
    TutorialItem(
        R.drawable.sc_helm,
        R.string.tut_helm,
        "_sCK1_j6rmA"
    ),
    TutorialItem(
        R.drawable.sc_sensors,
        R.string.tut_sensors,
        "GlVN7GjmklM"
    ),
    TutorialItem(
        R.drawable.sc_weapons,
        R.string.tut_weapons,
        "GDMjSs63HfQ"
    ),
    TutorialItem(
        R.drawable.sc_shields,
        R.string.tut_shields,
        "x9dZ0ovbQqo"
    ),
    TutorialItem(
        R.drawable.sc_tractorbeams,
        R.string.tut_tractor,
        "UP1kIlBmaNc"
    ),
    TutorialItem(
        R.drawable.sc_jumpdrive,
        R.string.tut_jump,
        "Rk6q2mvz5YE"
    ),
    TutorialItem(
        R.drawable.sc_damagecontrol,
        R.string.tut_damage,
        "3sUOV6RTI4E"
    )
)

@Composable
fun Tutorials(
    timerViewModel: TimerViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .align(Alignment.CenterEnd)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.tutorials_title),
                    color = Color.White,
                    fontSize = 6.em
                )
                Spacer(Modifier.width(20.dp))
                Button(
                    onClick = {
                        timerViewModel.setPhase(Phase.NEMESIS_START)
                    }
                ) {
                    Text(stringResource(R.string.button_back))
                }
            }
            TutorialList(tutorialList)
        }
    }
}

@Composable
fun TutorialList(tutorials: List<TutorialItem>) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        tutorials.forEach { tutorial ->
            TutorialRow(tutorial)
        }
    }
}

@Composable
@Preview
fun TutorialListPreview() {
    TutorialList(tutorialList)
}

@Composable
fun TutorialRow(tutorial: TutorialItem) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .clickable(
                onClick = {
                    Log.d("TUT", "Clicked ${tutorial.titleId}")
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/watch?v=${tutorial.youtube}")
                    )
                    context.startActivity(intent)
                }
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(tutorial.iconId),
            contentDescription = null,
            modifier = Modifier.size(60.dp)
        )
        Spacer(
            modifier = Modifier.fillMaxWidth(0.05f)
        )
        Text(
            text = stringResource(tutorial.titleId),
            color = Color.White,
            fontSize = 6.em
        )
    }
}