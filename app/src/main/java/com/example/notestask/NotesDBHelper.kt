package com.example.notestask

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.view.View

class NotesDBHelper(c: Context):SQLiteOpenHelper(c,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION=1
        private const val DATABASE_NAME="notes.db"
        const val TABLE_NAME="notes"
        const val COLUMN_ID="id"
        const val COLUMN_TITLE="title"
        const val COLUMN_CONTENT="content"
    }
    override fun onCreate(db:SQLiteDatabase?){
        val createTableQuery="CREATE TABLE $TABLE_NAME ("+
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "$COLUMN_TITLE TEXT,"+
                "$COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}
data class Note(val id:Long=-1,val title:String,val content:String)
class NoteRepository(context: Context){
    private val dbHelper=NotesDBHelper(context)
    fun addOrUpdateNote(note:Note):Long{
        val db=dbHelper.writableDatabase
        val values=ContentValues().apply {
                put(NotesDBHelper.COLUMN_TITLE,note.title)
                put(NotesDBHelper.COLUMN_CONTENT,note.content)
        }
        val id=if(note.id==-1L){
           db.insert(NotesDBHelper.TABLE_NAME,null,values)
        }
        else{
            db.update(NotesDBHelper.TABLE_NAME,values,"${NotesDBHelper.COLUMN_ID} = ?", arrayOf(note.id.toString()))
            note.id
        }
        db.close()
        return id
    }
    fun deleteNoteById(id:Long):Int{
        val db=dbHelper.writableDatabase
        val deletedRows=db.delete(NotesDBHelper.TABLE_NAME,"${NotesDBHelper.COLUMN_ID} = ?", arrayOf(id.toString()))
        db.close()
        return deletedRows
    }
    fun getAllNotes():List<Note>{
        val notes= mutableListOf<Note>()
        val db=dbHelper.readableDatabase
        val cursor=db.rawQuery("SELECT * FROM ${NotesDBHelper.TABLE_NAME}",null)
        with(cursor){
            while(moveToNext()){
                val id=getLong(getColumnIndexOrThrow(NotesDBHelper.COLUMN_ID))
                val title=getString(getColumnIndexOrThrow(NotesDBHelper.COLUMN_TITLE))
                val content=getString(getColumnIndexOrThrow(NotesDBHelper.COLUMN_CONTENT))
                notes.add(Note(id,title,content))
            }
        }
        cursor.close()
        db.close()
        return notes
    }
}