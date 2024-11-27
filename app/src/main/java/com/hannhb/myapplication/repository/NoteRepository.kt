package com.hannhb.myapplication.repository

import com.hannhb.myapplication.data.NoteDatabaseDao
import com.hannhb.myapplication.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDatabaseDao: NoteDatabaseDao
) {
    suspend fun addNote(note: Note) = noteDatabaseDao.insert(note)

    suspend fun update(note: Note) = noteDatabaseDao.update(note)
    suspend fun delete(note: Note) = noteDatabaseDao.delete(note)
    suspend fun deleteAllNote() = noteDatabaseDao.deleteAll()
    fun getAllNotes(): Flow<List<Note>> = noteDatabaseDao.getNotes().flowOn(Dispatchers.IO)
        .conflate()

}