package it.polito.lab3.data3

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import it.polito.lab3.AppDatabase
import it.polito.lab3.R
import it.polito.lab3.models.Sport
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader

class StartingSports(private val context: Context) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            fillWithStartingNotes(context)
        }
    }

    private fun loadJSONArray(context: Context): JSONArray {

        val inputStream = context.resources.openRawResource(R.raw.sports)

        BufferedReader(inputStream.reader()).use {
            return JSONArray(it.readText())
        }
    }

    private suspend fun fillWithStartingNotes(context: Context){

        val dao = AppDatabase.getDatabase(context)?.sportDao()

        try {
            val notes = loadJSONArray(context)
            if (notes != null){
                for (i in 0 until notes.length()){
                    val item = notes.getJSONObject(i)
                    val noteTitle = item.getString("title")
                    val notesDescription = item.getString("note")

                    val noteEntity = Sport(
                        noteTitle,notesDescription
                    )

                    dao?.insert(noteEntity)
                }
            }
        }

        catch (e: JSONException) {
        }
    }
}