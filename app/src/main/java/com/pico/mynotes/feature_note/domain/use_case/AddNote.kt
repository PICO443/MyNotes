package com.pico.mynotes.feature_note.domain.use_case

import com.pico.mynotes.feature_note.domain.model.InvalidNoteException
import com.pico.mynotes.feature_note.domain.model.Note
import com.pico.mynotes.feature_note.domain.repository.NoteRepository

class AddNote(private val repository: NoteRepository) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) throw InvalidNoteException("Note title can't be empty !.")
        if (note.content.isBlank()) throw InvalidNoteException("Note content can't be empty !.")
        repository.addNote(note)
    }
}