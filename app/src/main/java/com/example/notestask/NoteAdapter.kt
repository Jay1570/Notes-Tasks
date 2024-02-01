package com.example.notestask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private val notes:List<Note>,private val onItemClick:(Note)->Unit,
                  private val onDeleteClick:(Note)-> Unit ):RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    inner class NoteViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val noteTitle: TextView = itemView.findViewById(R.id.noteTitle)
        val noteContent: TextView = itemView.findViewById(R.id.noteContent)
        val btnDelete:Button=itemView.findViewById(R.id.deleteNote)

        init {
            itemView.setOnClickListener {
                onItemClick(notes[adapterPosition])
            }
            btnDelete.setOnClickListener{
                onDeleteClick(notes[adapterPosition])
            }
        }
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