package com.example.notestask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext

class AddNote : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        val noteTitle=findViewById<EditText>(R.id.editNoteTitle)
        val noteContent=findViewById<EditText>(R.id.editNoteContent)
        val save=findViewById<Button>(R.id.btnSaveNote)
        save.setOnClickListener{
            val newTitle=noteTitle.text.toString().trim()
            val newContent=noteContent.text.toString().trim()
            if(newTitle.isNotEmpty() && newContent.isNotEmpty()) {
                val noteRepository = NoteRepository(this)
                val note = Note(title = newTitle, content = newContent)
                val newNoteId = noteRepository.addOrUpdateNote(note)
                if (newNoteId != -1L) {
                    Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }
            }
            else{
                Toast.makeText(this, "Title Or Content Is Empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}