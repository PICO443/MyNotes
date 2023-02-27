package com.pico.mynotes.feature_note.domain.use_case

import com.pico.mynotes.feature_note.domain.model.Note
import com.pico.mynotes.feature_note.domain.repository.NoteRepository
import com.pico.mynotes.feature_note.domain.util.NoteOrder
import com.pico.mynotes.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(private val repository: NoteRepository) {

    operator fun invoke(noteOrder: NoteOrder): Flow<List<Note>> {
        return repository.getNotes().map { noteList ->
            when (noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> noteList.sortedBy { it.title.lowercase() }
                        is NoteOrder.Date -> noteList.sortedBy { it.timeStamp }
                        is NoteOrder.Color -> noteList.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> noteList.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Date -> noteList.sortedByDescending { it.timeStamp }
                        is NoteOrder.Color -> noteList.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}