package de.syntax_institut.taskmanager.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: Todo)

    @Query("SELECT * from tbl_todoList ORDER BY id ASC")
    fun getAllItems(): Flow<List<Todo>>

    @Query("SELECT * from tbl_todoList ORDER BY todoText")
    fun getAllItemsSorted(): Flow<List<Todo>>

    @Delete
    suspend fun delete(todo: Todo)

    @Update
    suspend fun update(todo: Todo)
}
