package com.hannhb.myapplication.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.hannhb.myapplication.data.NoteDataSource
import com.hannhb.myapplication.model.Note

class NoteViewModel: ViewModel() {
    private val lstNote = mutableStateListOf<Note>()

    init {
        lstNote.addAll(NoteDataSource().loadNotes())
    }

    fun addNote(note: Note){
        lstNote.add(note)
    }

    fun removeNote(note: Note) {
        lstNote.remove(note)
    }

    fun getAllNotes() :List<Note> {
        return lstNote
    }
}