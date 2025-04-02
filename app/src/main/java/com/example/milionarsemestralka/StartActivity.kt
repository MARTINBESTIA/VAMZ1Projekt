package com.example.milionarsemestralka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.milionarsemestralka.ui.theme.MilionarSemestralkaTheme

class StartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    val buttonImage = painterResource(R.drawable.menubutton)
    val logoImage = painterResource(R.drawable.logo)
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center//https://developer.android.com/codelabs/basic-android-kotlin-compose-add-images#5
    ) {
        Image(
            painter = logoImage,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(1f)
                .aspectRatio(16f / 9f) // chatgpt modifier

        )
        Image(
            painter = buttonImage,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .aspectRatio(16f / 9f) // chatgpt modifier
        )
        Image(
            painter = buttonImage,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .aspectRatio(16f / 9f) // chatgpt modifier
        )
        Image(
            painter = buttonImage,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .aspectRatio(16f / 9f) // chatgpt modifier
        )

    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

