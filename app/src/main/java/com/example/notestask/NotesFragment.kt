package com.example.notestask

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class NotesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_notes, container, false)
        val recyclerNotes = view?.findViewById<RecyclerView>(R.id.recyclerNotes)
        recyclerNotes?.layoutManager =LinearLayoutManager(requireContext())
        val noteRepository=NoteRepository(requireContext())
        val notes=noteRepository.getAllNotes()
        noteAdapter= NoteAdapter(notes,onItemClick = {clickedNote ->
            val intent=Intent(requireContext(),EditNote::class.java)
            intent.putExtra("noteId",clickedNote.id)
            startActivity(intent)
        }, onDeleteClick = {clickedNote ->
           deleteNote(clickedNote)
        })

        recyclerNotes?.adapter=noteAdapter
        val fabAddNote:ExtendedFloatingActionButton=view.findViewById(R.id.fabAddNote)
        fabAddNote.setOnClickListener(){
            val intent=Intent(requireContext(),AddNote::class.java)
            startActivity(intent)
        }
        recyclerNotes?.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(dy>0&&fabAddNote.isExtended){
                    fabAddNote.shrink()
                }else if(dy<0&&!fabAddNote.isExtended){
                    fabAddNote.extend()
                }
            }
        })
        return view
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun deleteNote(note:Note){
        val noteRepository=NoteRepository(requireContext())
        noteRepository.deleteNoteById(note.id)
        noteAdapter.notifyDataSetChanged()
        requireActivity().recreate()
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotesFragment()
    }
}