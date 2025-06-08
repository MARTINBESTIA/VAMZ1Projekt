package com.example.milionarsemestralka

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalDensity
import data.SoundManager
import domain.GameSessionController
import kotlinx.coroutines.delay

class PrizeLadderActivity : androidx.activity.ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SoundManager.initialize(this)
        if (GameSessionController.stillInGame.value) {
            SoundManager.playLadderActivitySound()
        }
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { outerPadding ->
            PrizeLadderBg(
                modifier = Modifier.padding(outerPadding))
            ButtonsLayout(
                modifier = Modifier
                    .padding(outerPadding)
                    .fillMaxSize()
                )
                if (GameSessionController.stillInGame.value) {
                    MovingRectangleScreen()
                    GoToHotSeatActivity(LocalContext.current)
                }
                EndGameDialog()
            }
        }
    }
}


@Composable
fun PrizeLadderBg(modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.backgroundmain)
    val imageModifier = Modifier
        .fillMaxSize()   // https://developer.android.com/develop/ui/compose/modifiers
    Image(
        painter = image,
        contentDescription = null,
        contentScale = ContentScale.FillBounds, // https://developer.android.com/develop/ui/compose/graphics/images/customize
        modifier = imageModifier
    )
}


@Composable
fun ButtonsLayout(modifier: Modifier = Modifier) {
    val prizes = intArrayOf(500, 1000, 2000, 3000, 5000, 7500, 10000, 12500, 15000, 25000, 50000, 100000, 250000, 500000, 1000000)
    val prizesReversed = prizes.reversed()
    LazyColumn (
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly) {
        items(15) { index ->
            var painterSource = painterResource(R.drawable.menubutton)
            when (index) {
                10 -> painterSource = painterResource(R.drawable.lvl5pic)
                5 -> painterSource = painterResource(R.drawable.lvl10pic)
                0 -> painterSource = painterResource(R.drawable.lvl10pic)
                else -> painterSource = painterResource(R.drawable.basiclvl)
            }
            Box(
              modifier = Modifier
            ) {
                var maxWidth = 0.85f;
                if (index == 0) maxWidth = 1.0f;
                if (index == 5) maxWidth = 0.93f;
                Image(
                    painter = painterSource,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(maxWidth)
                        .aspectRatio(8f)
                )
                Text(
                    text = (15 - index).toString() + ".",
                    fontSize = 29.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(10.dp)
                )
                Text(
                    text =  prizesReversed[index].toString() + " €",
                    fontSize = 29.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }

    }
}

@Composable
fun MovingRectangleScreen() {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

    val currentLevel = GameSessionController.currentLevel.coerceIn(1, 15)
    val targetY = screenHeightPx * (1f - (currentLevel - 1) / 14f) - 160f

    val offsetY = remember { Animatable(0f) }
    val alphaAnim = remember { Animatable(0f) }

    LaunchedEffect(currentLevel, screenHeightPx) { // s animaciami pomahal chatgpt
        offsetY.animateTo(
            targetValue = targetY,
            animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
        )

        repeat(3) {
            alphaAnim.animateTo(0.1f, animationSpec = tween(500))
            alphaAnim.animateTo(0.5f, animationSpec = tween(500))
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .offset { IntOffset(0, offsetY.value.toInt()) }
                .alpha(alphaAnim.value)
                .background(Color.Yellow)
                .border(2.dp, Color.Red)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(50.dp),
                    clip = false
                )
        )
    }
}



@Composable
fun GoToHotSeatActivity(context: Context) {
    LaunchedEffect(Unit) { // s animaciami pomahal chatgpt
        delay(5000)
        val intent = Intent(context, HotSeatActivity::class.java)
        context.startActivity(intent)
        (context as? Activity)?.finish() // chatgpt error fix
    }
}

@Composable
fun EndGameDialog() {
    val isInGame = GameSessionController.stillInGame.value
    val context = LocalContext.current
    val prizes = intArrayOf(0, 500, 1000, 2000, 3000, 5000, 7500, 10000, 12500, 15000, 25000, 50000, 100000, 250000, 500000, 1000000)

    if (!isInGame) {
        AlertDialog(
            onDismissRequest = {
                GameSessionController.resetGame()
                val intent = Intent(context, StartActivity::class.java)
                context.startActivity(intent)
                (context as? Activity)?.finish()
            },
            title = { Text(text = "You are the winner!") },
            text = {
                Text("Congratulations! You won ${prizes[GameSessionController.currentLevel - 1]} €")
            },
            confirmButton = {
                Button(onClick = {
                    GameSessionController.resetGame()
                    val intent = Intent(context, StartActivity::class.java)
                    context.startActivity(intent)
                    (context as? Activity)?.finish()
                }) {
                    Text("OK")
                }
            }
        )
    }
}




