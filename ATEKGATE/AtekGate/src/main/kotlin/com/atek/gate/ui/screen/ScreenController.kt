package com.atek.gate.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.atek.gate.utils.Constants
import ui.screen.*

@Composable
fun ScreenController(
    modifier: Modifier,
    state: Constants.SCREENS,
) {
    when (state) {
        Constants.SCREENS.CONFIG -> {
            ConfigScreen(
                modifier = modifier
            )
        }
        Constants.SCREENS.UPDATE -> {
            UpdateScreen(
                modifier = modifier
            )
        }
        Constants.SCREENS.MAIN -> {
            NormalScreen(
                modifier = modifier
            )
        }
        Constants.SCREENS.ENTRY -> {
            EntryScreen(
                modifier = modifier
            )
        }
        Constants.SCREENS.EXIT -> {
            ExitScreen(
                modifier = modifier
            )
        }
        Constants.SCREENS.ERROR -> {
            ErrorScreen(
                modifier = modifier,
            )
        }
    }
}