package com.pico.mynotes.feature_note.domain.model

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
){
    companion object {
        val noteColors = emptyList<Int>()
    }
}

class InvalidNoteException(message: String): Exception(message)
