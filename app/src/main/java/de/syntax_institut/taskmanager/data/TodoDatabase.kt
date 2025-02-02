package de.syntax_institut.taskmanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 1, exportSchema = false)

abstract class TodoDatabase : RoomDatabase() {
    abstract fun toDoDatabase(): TodoDAO

    companion object {
        @Volatile
        private var Instance: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TodoDatabase::class.java, "katana_note_database")
                    .build().also { Instance = it }
            }
        }
    }
}
