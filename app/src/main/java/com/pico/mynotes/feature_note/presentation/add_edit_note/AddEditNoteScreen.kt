package com.pico.mynotes.feature_note.presentation.add_edit_note

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pico.mynotes.R
import com.pico.mynotes.feature_note.domain.model.Note
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    onNoteSave: () -> Unit
) {
    val titleState = viewModel.noteTitle
    val contentState = viewModel.noteContent
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SaveNote -> {
                    onNoteSave()
                }
                is UiEvents.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message, duration = SnackbarDuration.Short)
                }
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(title = { Text(text = "Edit Note") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onEvent(AddEditNoteEvents.SaveNote) }) {
                Icon(
                    painter = painterResource(R.drawable.save_fill0_wght400_grad0_opsz48),
                    contentDescription = "Save",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Title")
                TextField(
                    value = titleState.value,
                    placeholder = { Text(text = "Enter Title") },
                    onValueChange = { viewModel.onEvent(AddEditNoteEvents.EnterTitle(it)) }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Content")
                TextField(
                    value = contentState.value,
                    placeholder = { Text("Enter Content") },
                    onValueChange = { viewModel.onEvent(AddEditNoteEvents.EnterContent(it)) }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row() {
                Text(text = "Color")
                Spacer(modifier = Modifier.width(16.dp))
                Note.noteColors.forEach {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(it)
                            .clickable { viewModel.onEvent(AddEditNoteEvents.ChangeColor(it.toArgb())) }
                            .border(
                                width = if (viewModel.noteColor.value == it.toArgb()) 4.dp else 0.dp,
                                color = Color.Gray
                            )
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    }
}