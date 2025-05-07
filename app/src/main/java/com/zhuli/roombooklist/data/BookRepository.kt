package com.zhuli.roombooklist.data

import androidx.annotation.WorkerThread
import com.zhuli.roombooklist.network.BookNetworkRepository
import kotlinx.coroutines.flow.Flow

class BookRepository(
    private val bookDao: BookDao,
    private val networkRepository: BookNetworkRepository
) {
    
    // Get all books, returns Flow
    val allBooks: Flow<List<Book>> = bookDao.getAllBooks()
    
    //Fetch data from the server, return true for success and false for failure
    @WorkerThread
    suspend fun refreshBooksFromNetwork(): Boolean {
        val result = networkRepository.getBooks()
        return result.fold(
            onSuccess = { books ->
                // clear local cache
                bookDao.deleteAllBooks()
                // insert newest data
                bookDao.insertBooks(books)
                true
            },
            onFailure = {
                false
            }
        )
    }
}