package com.example.notestask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class EditNote : AppCompatActivity() {
    private lateinit var updateTitle:EditText
    private lateinit var updateContent:EditText
    private lateinit var save:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)
        val noteId=intent.getLongExtra("noteId",-1)
        updateTitle=findViewById(R.id.updateNoteTitle)
        updateContent=findViewById(R.id.updateNoteContent)
        save=findViewById(R.id.btnUpdateNote)
        val noteRepository=NoteRepository(this)
        val n1 = noteRepository.getNoteById(noteId)
        updateTitle.setText(n1.title)
        updateContent.setText(n1.content)
        save.setOnClickListener{
            val title=updateTitle.text.toString().trim()
            val content=updateContent.text.toString().trim()
            if(title.isNotEmpty() && content.isNotEmpty()){
                val updatedNote=Note(noteId,title,content)
                val rowsAffected=noteRepository.addOrUpdateNote(updatedNote)
                if(rowsAffected>0){
                    Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,MainActivity::class.java))
                    finishAffinity()
                }
            }
            else{
                Toast.makeText(this, "Title Or Content Is Empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}