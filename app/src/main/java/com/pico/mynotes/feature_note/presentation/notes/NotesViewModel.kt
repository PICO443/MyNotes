package com.pico.mynotes.feature_note.presentation.notes

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pico.mynotes.feature_note.domain.model.Note
import com.pico.mynotes.feature_note.domain.use_case.NoteUseCases
import com.pico.mynotes.feature_note.domain.util.NoteOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val noteUseCases: NoteUseCases) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state = _state

    private var recentlyDeletedNote: Note? = null

    private var job: Job? = null

    fun onEvent(event: NotesEvents) {
        when (event) {
            is NotesEvents.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NotesEvents.Order -> {
                if ((event.noteOrder::class == _state.value.noteOrder::class) && (event.noteOrder.orderType == _state.value.noteOrder.orderType)) return
                getNotes(event.noteOrder)
            }
            NotesEvents.RestoreNote -> {
                viewModelScope.launch {
                    recentlyDeletedNote?.let {
                        noteUseCases.addNote(it)
                        recentlyDeletedNote = null
                    }
                }
            }
            NotesEvents.ToggleOrderSection -> {
                _state.value =
                    _state.value.copy(isOrderSelectionVisible = _state.value.isOrderSelectionVisible.not())
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        job?.cancel()
        job = noteUseCases.getNotes(noteOrder).onEach {
            _state.value = _state.value.copy(notes = it, noteOrder = noteOrder)
        }.launchIn(viewModelScope)
    }
}