package com.pico.mynotes.feature_note.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String,
    val content: String,
    val timeStamp: Long,
    val color: Int,
    @PrimaryKey
    val id: Int? = null
) {
    companion object {
        val noteColors = listOf(
            Color(207, 148, 218), Color(232, 236, 155), Color(255, 171, 145), Color(
                130, 222, 234
            )
        )
    }
}

class InvalidNoteException(message: String) : Exception(message)
