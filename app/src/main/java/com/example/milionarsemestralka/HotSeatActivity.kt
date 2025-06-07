package com.example.milionarsemestralka

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.lifecycleScope
import data.AppDatabase
import domain.GameSessionController
import domain.QuestionProcessor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HotSeatActivity : androidx.activity.ComponentActivity() {
    private lateinit var questionProcessor: QuestionProcessor
    override fun onCreate(savedInstanceState: Bundle?) {
        val questionDao = AppDatabase.getDatabase(this).questionsDao()
        questionProcessor = QuestionProcessor(
            questionDao,
            (1..15).random(),
            (1..15).random()
        )
        lifecycleScope.launch {
            questionProcessor.loadQuestion()
        }
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { outerPadding ->
                HotSeatBg(
                    modifier = Modifier.padding(outerPadding)
                )
                HotSeatLayout(
                    modifier = Modifier.padding(outerPadding),
                    questionProcessor
                )
            }
        }
    }
}

@Composable
fun HotSeatBg(modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.hotseatbg)
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
fun HotSeatLayout(modifier: Modifier = Modifier, questionProcessor: QuestionProcessor) {
    val context = LocalContext.current

    val answered = remember { mutableStateOf(false) }
    val buttonColorA = remember { mutableStateOf<ColorFilter?>(null) }
    val buttonColorB = remember { mutableStateOf<ColorFilter?>(null) }
    val buttonColorC = remember { mutableStateOf<ColorFilter?>(null) }
    val buttonColorD = remember { mutableStateOf<ColorFilter?>(null) }

    val scope = rememberCoroutineScope()

    fun handleAnswerClick(answer: Char, questionProcessor: QuestionProcessor) {
        if (answered.value) return
        answered.value = true

        if (answer == questionProcessor.getCorrectAnswer()) {
            when (answer) {
                'A' -> buttonColorA.value = ColorFilter.tint(Color.Green)
                'B' -> buttonColorB.value = ColorFilter.tint(Color.Green)
                'C' -> buttonColorC.value = ColorFilter.tint(Color.Green)
                'D' -> buttonColorD.value = ColorFilter.tint(Color.Green)
            }
            GameSessionController.nextLevel()
        } else {
            when (questionProcessor.getCorrectAnswer()) {
                'A' -> buttonColorA.value = ColorFilter.tint(Color.Green)
                'B' -> buttonColorB.value = ColorFilter.tint(Color.Green)
                'C' -> buttonColorC.value = ColorFilter.tint(Color.Green)
                'D' -> buttonColorD.value = ColorFilter.tint(Color.Green)
            }
            when (answer) {
                'A' -> buttonColorA.value = ColorFilter.tint(Color.Red)
                'B' -> buttonColorB.value = ColorFilter.tint(Color.Red)
                'C' -> buttonColorC.value = ColorFilter.tint(Color.Red)
                'D' -> buttonColorD.value = ColorFilter.tint(Color.Red)
            }
            GameSessionController.endGame()
        }

        // Delay then navigate
        scope.launch {
            kotlinx.coroutines.delay(1500L)
            val intent = Intent(context, PrizeLadderActivity::class.java)
            context.startActivity(intent)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
            ) {
                Image(
                    painter = painterResource(R.drawable.questionbox),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .aspectRatio(1f)
                        .align(Alignment.Center)
                )
                Text(
                    text = questionProcessor.getQuestion()!!,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.78f)
                        .align(Alignment.Center)
                )
            }
            //Spacer(modifier = Modifier.height(5.dp))
            Box(
                modifier = Modifier
            ) {
                Button(
                    onClick = { handleAnswerClick('A', questionProcessor) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(1.5f),
                    content = {
                        Image(
                            painter = painterResource(R.drawable.question),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .aspectRatio(8.5f),
                            contentScale = ContentScale.FillBounds,
                            colorFilter = buttonColorA.value
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    enabled = !answered.value
                )
                Text(
                    text = "A.",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 65.dp)
                )
                Text(
                    text = questionProcessor.getAnswerA()!!,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Box(
                modifier = Modifier
            ) {
                Button(
                    onClick = { handleAnswerClick('B', questionProcessor) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(1.5f),
                    content = {
                        Image(
                            painter = painterResource(R.drawable.question),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .aspectRatio(8.5f),
                            contentScale = ContentScale.FillBounds,
                            colorFilter = buttonColorB.value
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    enabled = !answered.value
                )
                Text(
                    text = "B.",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 65.dp)
                )
                Text(
                    text = questionProcessor.getAnswerB()!!,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Box(
                modifier = Modifier
            ) {
                Button(
                    onClick = { handleAnswerClick('C', questionProcessor) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(1.5f),
                    content = {
                        Image(
                            painter = painterResource(R.drawable.question),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .aspectRatio(8.5f),
                            contentScale = ContentScale.FillBounds,
                            colorFilter = buttonColorC.value
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    enabled = !answered.value
                )
                Text(
                    text = "C.",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 65.dp)
                )
                Text(
                    text = questionProcessor.getAnswerC()!!,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Box(
                modifier = Modifier
            ) {
                Button(
                    onClick = { handleAnswerClick('D', questionProcessor) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(1.5f),
                    content = {
                        Image(
                            painter = painterResource(R.drawable.question),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .aspectRatio(8.5f),
                            contentScale = ContentScale.FillBounds,
                            colorFilter = buttonColorD.value
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    enabled = !answered.value
                )
                Text(
                    text = "D.",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 65.dp)
                )
                Text(
                    text = questionProcessor.getAnswerD()!!,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Box(
                    modifier = Modifier
                ) {
                    Button(
                        onClick = { /* handle click */ },
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .scale(1.5f),
                        content = {
                            Image(
                                painter = painterResource(R.drawable._050),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .scale(2f),
                                contentScale = ContentScale.FillBounds
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    )
                }
                Box(
                    modifier = Modifier
                ) {
                    Button(
                        onClick = { /* handle click */ },
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .scale(1.5f),
                        content = {
                            Image(
                                painter = painterResource(R.drawable.audience),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .scale(2f),
                                contentScale = ContentScale.FillBounds
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    )
                }
                Box(
                    modifier = Modifier
                ) {
                    Button(
                        onClick = { /* handle click */ },
                        modifier = Modifier
                            .padding(horizontal = 18.dp)
                            .scale(1.5f),
                        content = {
                            Image(
                                painter = painterResource(R.drawable.lifeline),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(horizontal = 18.dp)
                                    .scale(2f),
                                contentScale = ContentScale.FillBounds
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    )
                }
                Box(
                    modifier = Modifier
                ) {
                    Button(
                        onClick = { /* handle click */ },
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .scale(1.5f),
                        content = {
                            Image(
                                painter = painterResource(R.drawable.door),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .scale(2f),
                                contentScale = ContentScale.FillBounds
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}



