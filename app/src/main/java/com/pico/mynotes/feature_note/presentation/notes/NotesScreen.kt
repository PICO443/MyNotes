package com.pico.mynotes.feature_note.presentation.notes

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pico.mynotes.R
import com.pico.mynotes.feature_note.presentation.notes.components.NoteListItem
import com.pico.mynotes.feature_note.presentation.notes.components.NotesOrderSection
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    viewModel: NotesViewModel = hiltViewModel(),
    onNoteClicked: (Int?) -> Unit,
    onAddNewNote: () -> Unit
) {
    val state = viewModel.state.value
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(title = { Text(text = "My Notes") }, actions = {
                IconButton(onClick = { viewModel.onEvent(NotesEvents.ToggleOrderSection) }) {
                    Icon(
                        painter = painterResource(R.drawable.sort_fill0_wght400_grad0_opsz48),
                        contentDescription = "Sort Notes",
                        modifier = Modifier.size(24.dp)
                    )
                }
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAddNewNote() }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add new note")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            item {
                AnimatedVisibility(
                    visible = state.isOrderSelectionVisible,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    NotesOrderSection(
                        onNoteOrderChange = { viewModel.onEvent(NotesEvents.Order(it)) },
                        modifier = Modifier.fillMaxWidth(),
                        noteOrder = state.noteOrder
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            items(state.notes) { note ->
                NoteListItem(
                    modifier = Modifier
                        .clickable { onNoteClicked(note.id) }
                        .fillMaxWidth(),
                    note = note,
                    onDeleteClicked = {
                        viewModel.onEvent(NotesEvents.DeleteNote(note))
                        coroutineScope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = "Note Deleted!",
                                actionLabel = "Undo"
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                viewModel.onEvent(NotesEvents.RestoreNote)
                            }
                        }
                    })
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}