package com.example.milionarsemestralka

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.milionarsemestralka.ui.theme.MilionarSemestralkaTheme
import data.AppDatabase
import data.SoundManager
import data.BulkInsertion

class StartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SoundManager.initialize(this)
        SoundManager.playStartActivitySound()
        val db = AppDatabase.getDatabase(this)
        val bulkInsertion = BulkInsertion(application)
        bulkInsertion.insertAllQuestions()
        enableEdgeToEdge()
        setContent {
            MilionarSemestralkaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { outerPadding ->

                    BackgroundImage(
                        modifier = Modifier.padding(outerPadding)
                    )

                    Buttons(
                        modifier = Modifier.padding(outerPadding)
                    )

                }
            }
        }
    }
}


@Composable
fun BackgroundImage(modifier: Modifier = Modifier) {
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
fun Buttons(modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val buttonImage = painterResource(R.drawable.menubutton)
    val logoImage = painterResource(R.drawable.logo)
    val buttonModifier = Modifier
        .fillMaxWidth(1f)
        .scale(1.5f)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = logoImage,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(0.3f)
            )
            Spacer(modifier = Modifier.height(50.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                Box (
                    modifier = Modifier
                ) {
                    Button(
                        onClick = {
                            SoundManager.stopStartActivitySound()
                            val intent = Intent(context, PrizeLadderActivity::class.java)
                            context.startActivity(intent)
                            (context as? Activity)?.finish()
                                  },
                        modifier = Modifier
                            .fillMaxWidth()
                            .scale(1.5f),
                        content = {
                            Image(
                                painter = painterResource(R.drawable.menubutton),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .aspectRatio(8.5f),
                                contentScale = ContentScale.FillBounds
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    )
                    Text(
                        text = "START GAME",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.height(25.dp))
                Box (
                    modifier = Modifier
                ) {
                    Button(
                        onClick = { showDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .scale(1.5f),
                        content = {
                            Image(
                                painter = painterResource(R.drawable.menubutton),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .aspectRatio(8.5f),
                                contentScale = ContentScale.FillBounds
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    )
                    Text(
                        text = "OPTIONS",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))
                Box (
                    modifier = Modifier
                ) {
                    Button(
                        onClick = { System.exit(0) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .scale(1.5f),
                        content = {
                            Image(
                                painter = painterResource(R.drawable.menubutton),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .aspectRatio(8.5f),
                                contentScale = ContentScale.FillBounds
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    )
                    Text(
                        text = "QUIT GAME",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
    if (showDialog) {
        VolumeDialog(onDismiss = { showDialog = false })
    }
}

@Composable
fun VolumeDialog(onDismiss: () -> Unit) {
    val musicVolume = remember { mutableStateOf(SoundManager.musicVolume) }
    val soundVolume = remember { mutableStateOf(SoundManager.soundVolume) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Adjust Volume") },
        text = {
            Column {
                Text("Music Volume: %.0f%%".format(musicVolume.value * 100))
                Slider(
                    value = musicVolume.value,
                    onValueChange = {
                        musicVolume.value = it
                        SoundManager.musicVolume = it
                        SoundManager.startActivityMusic?.setVolume(it, it)
                    },
                    valueRange = 0f..1f
                )
                Text("Sound Volume: %.0f%%".format(soundVolume.value * 100))
                Slider(
                    value = soundVolume.value,
                    onValueChange = {
                        soundVolume.value = it
                        SoundManager.soundVolume = it

                    },
                    valueRange = 0f..1f
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Done")
            }
        }
    )
}



