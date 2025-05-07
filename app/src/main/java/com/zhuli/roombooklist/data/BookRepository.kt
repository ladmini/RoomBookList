package com.zhuli.roombooklist.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class BookRepository(private val bookDao: BookDao) {
    
    // Get all books, returns Flow
    val allBooks: Flow<List<Book>> = bookDao.getAllBooks()
    
    // Insert book
    @WorkerThread
    suspend fun insert(book: Book) {
        bookDao.insertBook(book)
    }
    
    // Update book
    @WorkerThread
    suspend fun update(book: Book) {
        bookDao.updateBook(book)
    }
    
    // Delete book
    @WorkerThread
    suspend fun delete(book: Book) {
        bookDao.deleteBook(book)
    }
    
    // Get book by ID
    @WorkerThread
    suspend fun getBookById(id: Long): Book? {
        return bookDao.getBookById(id)
    }
}