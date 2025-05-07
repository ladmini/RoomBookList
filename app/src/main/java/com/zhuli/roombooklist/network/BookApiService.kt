package com.zhuli.roombooklist.network

import com.zhuli.roombooklist.constants.APIPath.Endpoints
import com.zhuli.roombooklist.data.Book
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApiService {
    @GET(Endpoints.BOOKS)
    suspend fun getBooks(): List<Book>
}