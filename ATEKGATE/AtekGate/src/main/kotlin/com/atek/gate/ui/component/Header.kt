package com.atek.gate.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.dp

@Composable
fun Header(modifier: Modifier) {
    Row(
        modifier = modifier.fillMaxSize().padding(20.dp)
    ) {
        Column(
            modifier = modifier.weight(1f).fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                useResource("/img/mmopl.png") { loadImageBitmap(it) },
                contentDescription = "Mumbai Metro One Logo",
                contentScale = ContentScale.Fit,
                modifier = modifier.fillMaxHeight()
            )
        }
        Column(
            modifier = modifier.weight(1f).fillMaxWidth(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                useResource("/img/atek.png") { loadImageBitmap(it) },
                contentDescription = "Mumbai Metro One Logo",
                contentScale = ContentScale.Fit,
                modifier = modifier.fillMaxHeight().padding(horizontal = 20.dp)
            )
        }
    }
}