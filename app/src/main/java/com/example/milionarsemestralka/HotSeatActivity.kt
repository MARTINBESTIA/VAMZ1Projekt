package com.example.milionarsemestralka

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.QuestionProcessor

class HotSeatActivity : androidx.activity.ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { outerPadding ->
                HotSeatBg(
                    modifier = Modifier.padding(outerPadding)
                )
                HotSeatLayout(
                    modifier = Modifier.padding(outerPadding)
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
fun HotSeatLayout(modifier: Modifier = Modifier) {
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
                )
                Text(
                    text = "Otázka",
                    color = androidx.compose.ui.graphics.Color.White,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
            //Spacer(modifier = Modifier.height(5.dp))
            Box(
                modifier = Modifier
            ) {
                Button(
                    onClick = { /* handle click */ },
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
                            contentScale = ContentScale.FillBounds
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                )
                Text(
                    text = "A.",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 65.dp)
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Box(
                modifier = Modifier
            ) {
                Button(
                    onClick = { /* handle click */ },
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
                            contentScale = ContentScale.FillBounds
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                )
                Text(
                    text = "B.",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 65.dp)
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Box(
                modifier = Modifier
            ) {
                Button(
                    onClick = { /* handle click */ },
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
                            contentScale = ContentScale.FillBounds
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                )
                Text(
                    text = "C.",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 65.dp)
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Box(
                modifier = Modifier
            ) {
                Button(
                    onClick = { /* handle click */ },
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
                            contentScale = ContentScale.FillBounds
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                )
                Text(
                    text = "D.",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 65.dp)
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
