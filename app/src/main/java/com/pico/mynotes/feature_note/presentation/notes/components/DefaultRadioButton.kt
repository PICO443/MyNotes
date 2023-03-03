package com.pico.mynotes.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DefaultRadioButton(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClicked: () -> Unit,
    text: String
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = selected, onClick = onClicked)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
    }
}

@Preview(showSystemUi = true)
@Composable
private fun DefaultRadioButtonPreview() {
    MaterialTheme() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DefaultRadioButton(
                selected = false,
                onClicked = { /*TODO*/ },
                text = "UnSelected Radio Button"
            )
            DefaultRadioButton(
                selected = true,
                onClicked = { /*TODO*/ },
                text = "Selected Radio Button"
            )
        }
    }
}