package com.pico.mynotes.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pico.mynotes.feature_note.domain.util.NoteOrder
import com.pico.mynotes.feature_note.domain.util.OrderType

@Composable
fun NotesOrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onNoteOrderChange: (NoteOrder) -> Unit
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            DefaultRadioButton(
                selected = noteOrder is NoteOrder.Title,
                onClicked = { onNoteOrderChange(NoteOrder.Title(noteOrder.orderType)) },
                text = "Title"
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                selected = noteOrder is NoteOrder.Date,
                onClicked = { onNoteOrderChange(NoteOrder.Date(noteOrder.orderType)) },
                text = "Date"
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                selected = noteOrder is NoteOrder.Color,
                onClicked = { onNoteOrderChange(NoteOrder.Color(noteOrder.orderType)) },
                text = "Color"
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            DefaultRadioButton(
                selected = noteOrder.orderType is OrderType.Ascending,
                onClicked = { onNoteOrderChange(noteOrder.copy(OrderType.Ascending)) },
                text = "Ascending"
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                selected = noteOrder.orderType is OrderType.Descending,
                onClicked = { onNoteOrderChange(noteOrder.copy(OrderType.Descending)) },
                text = "Descending"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showSystemUi = true)
@Composable
fun NotesOrderSectionPreview() {
    MaterialTheme() {
        NotesOrderSection(noteOrder = NoteOrder.Date(OrderType.Descending), onNoteOrderChange = {})
    }
}