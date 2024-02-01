package com.example.notestask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private val notes:List<Note>):RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    inner class NoteViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val noteTitle:TextView=itemView.findViewById(R.id.noteTitle)
        val noteContent:TextView=itemView.findViewById(R.id.noteContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote=notes[position]
        holder.noteTitle.text=currentNote.title
        holder.noteContent.text=currentNote.content
    }

    override fun getItemCount() = notes.size
}