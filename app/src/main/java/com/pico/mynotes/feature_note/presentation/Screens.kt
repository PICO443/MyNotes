package com.pico.mynotes.feature_note.presentation

sealed class Screens(val route: String) {
    object NotesScreen : Screens("notes_screen")
    object AddEditNoteScreen : Screens("add_edit_note_screen")
}
