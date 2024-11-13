package com.hannhb.myapplication.data

import com.hannhb.myapplication.model.Note

class NoteDataSource {
    fun loadNotes(): List<Note> {
        return listOf(
            Note(title = "Get up early", description = "at 5 AM"),
            Note(title = "Learn English", description = "at 5 AM"),
            Note(title = "go to bed", description = "at 5 AM")
        )
    }
}