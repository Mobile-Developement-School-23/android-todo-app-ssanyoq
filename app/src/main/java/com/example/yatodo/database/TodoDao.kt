package com.example.yatodo.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.yatodo.data.TodoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo_items")
    fun getItems(): Flow<List<TodoItem>>

    @Query("SELECT * FROM todo_items WHERE taskId = :id")
    fun getItem(id: String): TodoItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todoItem: TodoItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(todoItemList: List<TodoItem>)

    @Update
    fun updateItem(todoItem: TodoItem)

    @Delete
    fun deleteItem(todoItem: TodoItem)

    @Query("DELETE FROM todo_items")
    fun clearTable()
}
