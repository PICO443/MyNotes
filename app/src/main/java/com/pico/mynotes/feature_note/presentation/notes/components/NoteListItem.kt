package com.pico.mynotes.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.pico.mynotes.feature_note.domain.model.Note

@Composable
fun NoteListItem(modifier: Modifier = Modifier, note: Note, onDeleteClicked: (Note) -> Unit) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color(note.color),
//            contentColor = getContentColor(Color(note.color))
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = note.title, style = MaterialTheme.typography.titleLarge)
                IconButton(onClick = { onDeleteClicked(note) }) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "Delete Note")
                }
            }
            Text(text = note.content)

        }
    }
}

private fun isDark(color: Color): Boolean {
    return ColorUtils.calculateLuminance(color.toArgb()) < 0.5f
}

private fun getContentColor(color: Color): Color {
    return if (isDark(color)) {
        Color.White
    } else {
        Color.Black
    }
}

@Preview(showSystemUi = true)
@Composable
fun NoteListItemPreview() {
    MaterialTheme {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(16.dp)) {
            NoteListItem(
                note = Note(
                    "Title",
                    "Content that is to long for me to type im so lazy",
                    id = 1,
                    timeStamp = 5L,
                    color = 5
                ),
                onDeleteClicked = {}
            )
        }
    }
}