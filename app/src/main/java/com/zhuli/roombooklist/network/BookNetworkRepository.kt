package com.zhuli.roombooklist.network

import com.zhuli.roombooklist.data.Book

class BookNetworkRepository(private val apiService: BookApiService) {
    
    // Get all books using safe API call wrapper
    suspend fun getBooks(): Result<List<Book>> {
        return safeApiCall { apiService.getBooks() }
    }
    
    // Get book by specific ID using safe API call wrapper
    suspend fun getBookById(id: Long): Result<Book> {
        return safeApiCall { apiService.getBookById(id) }
    }
}