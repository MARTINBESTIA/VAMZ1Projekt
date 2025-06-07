package com.example.milionarsemestralka

import android.app.Activity
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.AppDatabase
import domain.GameSessionController
import domain.QuestionProcessor
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

    var questionProcessorInit by remember { mutableStateOf<QuestionProcessor?>(null) }
    val answered = remember { mutableStateOf(false) }

    val buttonColorA = remember { mutableStateOf<ColorFilter?>(null) }
    val buttonColorB = remember { mutableStateOf<ColorFilter?>(null) }
    val buttonColorC = remember { mutableStateOf<ColorFilter?>(null) }
    val buttonColorD = remember { mutableStateOf<ColorFilter?>(null) }
    val button5050 = remember { mutableStateOf<ColorFilter?>(null) }

    val buttonEnabledA = remember { mutableStateOf(true) }
    val buttonEnabledB = remember { mutableStateOf(true) }
    val buttonEnabledC = remember { mutableStateOf(true) }
    val buttonEnabledD = remember { mutableStateOf(true) }

    val clicked5050 = remember { mutableStateOf(false) }
    val clickedAudienceButton = remember { mutableStateOf(false) }
    val clickedHotlineButton = remember { mutableStateOf(false) }

    clicked5050.value = GameSessionController.isFiftyFiftyUsed.value
    if (clicked5050.value) {
        button5050.value = ColorFilter.tint(Color.Unspecified.copy(alpha = 0.3f))
    }
    clickedAudienceButton.value = GameSessionController.isAudienceHelpUsed.value
    clickedHotlineButton.value = GameSessionController.isStatisticsHelpUsed.value

    buttonEnabledA.value = true
    buttonEnabledB.value = true
    buttonEnabledC.value = true
    buttonEnabledD.value = true
    answered.value = false

    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        questionProcessor.loadQuestion()
        questionProcessorInit = questionProcessor
    }

    fun handle5050Click(answer: Char, questionProcessorP: QuestionProcessor?) {
        if (answered.value || clicked5050.value) return
        answered.value = true
        if (questionProcessorP == null) return
        if (clicked5050.value == true) return
        if (answer == 'A' || answer == 'C') {
            buttonColorB.value = ColorFilter.tint(Color.Red.copy(alpha = 0f))
            buttonColorD.value = ColorFilter.tint(Color.Red.copy(alpha = 0f))
            buttonEnabledB.value = false
            buttonEnabledD.value = false
        }
        else {
            buttonColorA.value = ColorFilter.tint(Color.Red.copy(alpha = 0f))
            buttonColorC.value = ColorFilter.tint(Color.Red.copy(alpha = 0f))
            buttonEnabledA.value = false
            buttonEnabledC.value = false
        }
        button5050.value = ColorFilter.tint(Color.Unspecified.copy(alpha = 0.3f))
        clicked5050.value = true
        GameSessionController.useFiftyFifty()
    }

    fun handleAnswerClick(answer: Char, questionProcessorP: QuestionProcessor?) {
        if (answered.value) return
        answered.value = true
        if (questionProcessorP == null) return
        if (answer == questionProcessorP.getCorrectAnswer()) {
            when (answer) {
                'A' -> buttonColorA.value = ColorFilter.tint(Color.Green)
                'B' -> buttonColorB.value = ColorFilter.tint(Color.Green)
                'C' -> buttonColorC.value = ColorFilter.tint(Color.Green)
                'D' -> buttonColorD.value = ColorFilter.tint(Color.Green)
            }
            GameSessionController.nextLevel()
        } else {
            when (questionProcessorP.getCorrectAnswer()) {
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
            (context as? Activity)?.finish()
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
                    text = if (questionProcessorInit == null) "Loading..." else questionProcessorInit?.getQuestion()!!,
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
                    onClick = { handleAnswerClick('A', questionProcessorInit) },
                    enabled = buttonEnabledA.value && !answered.value,
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
                )
                Text(
                    text = "A.",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 65.dp)
                )
                Text(
                    text = if (questionProcessorInit == null) "Loading..." else questionProcessorInit?.getAnswerA()!!,
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
                    onClick = { handleAnswerClick('B', questionProcessorInit) },
                    enabled = buttonEnabledB.value && !answered.value,
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
                )
                Text(
                    text = "B.",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 65.dp)
                )
                Text(
                    text = if (questionProcessorInit == null) "Loading..." else questionProcessorInit?.getAnswerB()!!,
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
                    onClick = { handleAnswerClick('C', questionProcessorInit) },
                    enabled = buttonEnabledC.value && !answered.value,
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
                )
                Text(
                    text = "C.",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 65.dp)
                )
                Text(
                    text = if (questionProcessorInit == null) "Loading..." else questionProcessorInit?.getAnswerC()!!,
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
                    onClick = { handleAnswerClick('D', questionProcessorInit) },
                    enabled = buttonEnabledD.value && !answered.value,
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
                )
                Text(
                    text = "D.",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 65.dp)
                )
                Text(
                    text = if (questionProcessorInit == null) "Loading..." else questionProcessorInit?.getAnswerD()!!,
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
                        onClick = { handle5050Click(questionProcessorInit?.getCorrectAnswer()!!, questionProcessorInit) },
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .scale(1.5f),
                        content = {
                            Image(
                                painter = painterResource(R.drawable._050),
                                colorFilter = button5050.value,
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



