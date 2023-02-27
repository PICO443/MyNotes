package com.pico.mynotes.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pico.mynotes.feature_note.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao
    companion object {
        const val DATA_BASE_NAME = "notes_db"
    }
}