package com.pico.mynotes.feature_note.presentation.add_edit_note

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pico.mynotes.feature_note.domain.model.InvalidNoteException
import com.pico.mynotes.feature_note.domain.model.Note
import com.pico.mynotes.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject() constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _noteTitle = mutableStateOf("")
    val noteTitle = _noteTitle

    private val _noteContent = mutableStateOf("")
    val noteContent = _noteContent

    private val _noteColor = mutableStateOf(Color(0, 200, 83, 255).toArgb())
    val noteColor = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId == -1) return@let
            viewModelScope.launch {
                noteUseCases.getNote(noteId)?.also { note ->
                    _noteTitle.value = note.title
                    _noteContent.value = note.content
                    _noteColor.value = note.color
                    currentNoteId = note.id
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvents) {
        when (event) {
            is AddEditNoteEvents.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNote(
                            Note(
                                title = noteTitle.value,
                                content = noteContent.value,
                                color = noteColor.value,
                                timeStamp = System.currentTimeMillis(),
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvents.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvents.ShowSnackbar(
                                message = e.message ?: "Couldn't save note"
                            )
                        )
                    }

                }
            }
            is AddEditNoteEvents.ChangeColor -> {
                _noteColor.value = event.color
            }
            is AddEditNoteEvents.EnterTitle -> {
                _noteTitle.value = event.title
            }
            is AddEditNoteEvents.EnterContent -> {
                _noteContent.value = event.content
            }
        }
    }


}

sealed class UiEvents() {
    data class ShowSnackbar(val message: String) : UiEvents()
    object SaveNote : UiEvents()
}