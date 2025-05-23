package com.example.milionarsemestralka

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class PrizeLadderActivity : androidx.activity.ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { outerPadding ->
            PrizeLadderBg(
                modifier = Modifier.padding(outerPadding))
            ButtonsLayout(
                modifier = Modifier
                    .padding(outerPadding)
                    .fillMaxSize()
                )
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