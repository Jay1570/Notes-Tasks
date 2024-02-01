package com.example.notestask

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NotesFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var recyclerNotes: RecyclerView
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
            Toast.makeText(requireContext(),"This Feature will be added Soon",Toast.LENGTH_SHORT).show()
        })

        recyclerNotes?.adapter=noteAdapter
        val fabAddNote:ExtendedFloatingActionButton=view.findViewById(R.id.fabAddNote)
        fabAddNote.setOnClickListener(){
            val intent=Intent(requireContext(),AddNote::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}