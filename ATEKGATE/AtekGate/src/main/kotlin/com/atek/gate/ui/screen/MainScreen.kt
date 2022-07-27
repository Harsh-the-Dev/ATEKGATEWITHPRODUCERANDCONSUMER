package com.atek.gate.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.atek.gate.ui.component.Footer
import com.atek.gate.ui.component.Header
import com.atek.gate.utils.Constants

@Composable
fun MainScreen(
    modifier: Modifier, state: Constants.SCREENS
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier.weight(.2f)
        ) {
            Header(
                modifier = Modifier
            )
        }

        Column(
            modifier = Modifier.weight(.7f)
        ) {
            ScreenController(
                modifier = modifier, state = state
            )
        }

        Column(
            modifier = Modifier.weight(.08f)
        ) {
            Footer(
                modifier = Modifier
            )
        }

    }
}