package com.pico.mynotes.feature_note.presentation.add_edit_note

sealed class AddEditNoteEvents {
    data class EnterTitle(val title: String) : AddEditNoteEvents()
    data class EnterContent(val content: String) : AddEditNoteEvents()
    data class ChangeColor(val color: Int) : AddEditNoteEvents()
    object SaveNote : AddEditNoteEvents()
}
