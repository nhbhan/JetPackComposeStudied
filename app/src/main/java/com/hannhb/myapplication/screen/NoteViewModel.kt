package com.hannhb.myapplication.screen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hannhb.myapplication.data.NoteDataSource
import com.hannhb.myapplication.model.Note
import com.hannhb.myapplication.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
): ViewModel() {
    private val _lstNote = MutableStateFlow<List<Note>>(emptyList())
    val lstNote = _lstNote.asStateFlow()
//    private val lstNote = mutableStateListOf<Note>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllNotes().distinctUntilChanged()
                .collect{listNote->
                    if (listNote.isNullOrEmpty()) {
                        Log.d("TAG", "empty list")
                    } else {
                        _lstNote.value = listNote
                    }
                }
        }
    }

     fun addNote(note: Note) = viewModelScope.launch {
        repository.addNote(note)
    }

     fun updateNote(note: Note) = viewModelScope.launch {
        repository.update(note)
    }

     fun removeNote(note: Note) = viewModelScope.launch {
            repository.delete(note)
        }
}