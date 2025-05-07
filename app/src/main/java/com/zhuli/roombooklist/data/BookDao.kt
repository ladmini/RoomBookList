package com.zhuli.roombooklist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getAllBooks(): Flow<List<Book>>
    
    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getBookById(id: Long): Book?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<Book>)
    
    @Update
    suspend fun updateBook(book: Book)
    
    @Delete
    suspend fun deleteBook(book: Book)
    
    @Query("DELETE FROM books")
    suspend fun deleteAllBooks()
}