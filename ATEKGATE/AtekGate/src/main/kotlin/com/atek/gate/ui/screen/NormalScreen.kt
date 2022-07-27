package ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NormalScreen(modifier: Modifier) {
    Row(
        modifier = modifier.fillMaxSize().padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.7f).fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {

            Text(
                text = "Welcome to Mumbai Metro One \nPresent your card or QR",
                color = Color.Black,
                fontSize = 42.sp,
                textAlign = TextAlign.Start,
                modifier = modifier.padding(5.dp),

                )
            Spacer(modifier = Modifier.padding(10.dp))

            Text(
                text = "मुंबई मेट्रो वन में आपका स्वागत है \nअपना कार्ड या QR प्रस्तुत करे",
                color = Color.Black,
                fontSize = 42.sp,
                modifier = modifier.padding(5.dp),
            )
            Spacer(modifier = Modifier.padding(10.dp))

            Text(
                text = "मुंबई मेट्रो वन मध्ये आपले स्वागत आहे \nतुमचे कार्ड किंवा QR सादर करा",
                color = Color.Black,
                fontSize = 42.sp,
                modifier = modifier.padding(5.dp),
            )
        }

        Column(
            modifier = Modifier,
        ) {
            Image(
                useResource("/img/card.jpeg") { loadImageBitmap(it) },
                contentDescription = "Mumbai Metro One Logo",
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
            Image(
                useResource("/img/qr.jpeg") { loadImageBitmap(it) },
                contentDescription = "Mumbai Metro One Logo",
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
        }
    }
}