package com.hannhb.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.hannhb.myapplication.data.NoteDataSource
import com.hannhb.myapplication.model.Note
import com.hannhb.myapplication.screen.NoteScreen
import com.hannhb.myapplication.screen.NoteViewModel
import com.hannhb.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(color = Color.White) {
                    val noteViewModel: NoteViewModel by viewModels()
                    NoteApp(noteViewModel = noteViewModel)

                }
            }
        }
    }
}

@Composable
fun NoteApp(noteViewModel: NoteViewModel) {
    val lstNote = noteViewModel.getAllNotes()
    NoteScreen(notes = lstNote,
        onAddNote = {noteViewModel.addNote(it)},
        onRemoveNote = {noteViewModel.removeNote(it)})

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}