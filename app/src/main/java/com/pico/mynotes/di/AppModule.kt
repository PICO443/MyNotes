package com.pico.mynotes.di

import android.content.Context
import androidx.room.Room
import com.pico.mynotes.feature_note.data.data_source.NoteDatabase
import com.pico.mynotes.feature_note.data.repository.NoteRepositoryImpl
import com.pico.mynotes.feature_note.domain.repository.NoteRepository
import com.pico.mynotes.feature_note.domain.use_case.DeleteNote
import com.pico.mynotes.feature_note.domain.use_case.GetNotes
import com.pico.mynotes.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            NoteDatabase.DATA_BASE_NAME
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(database: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(database.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository)
        )
    }
}