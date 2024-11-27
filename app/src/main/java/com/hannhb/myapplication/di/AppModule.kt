package com.hannhb.myapplication.di

import android.content.Context
import androidx.room.Room
import com.hannhb.myapplication.data.NoteDatabase
import com.hannhb.myapplication.data.NoteDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideNotesDao(noteDatabase: NoteDatabase): NoteDatabaseDao
    = noteDatabase.noteDatabaseDao()

    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(context, NoteDatabase::class.java, "notes.db")
            .fallbackToDestructiveMigration().build()
    }
}