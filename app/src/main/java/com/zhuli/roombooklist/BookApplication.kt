package com.zhuli.roombooklist

import android.app.Application
import com.zhuli.roombooklist.data.AppDatabase
import com.zhuli.roombooklist.data.BookRepository
import com.zhuli.roombooklist.network.BookNetworkRepository
import com.zhuli.roombooklist.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class BookApplication : Application() {
    
    private val applicationScope = CoroutineScope(SupervisorJob())
    
    private val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    private val networkRepository by lazy { BookNetworkRepository(RetrofitClient.bookApiService) }
    val repository by lazy { BookRepository(database.bookDao(), networkRepository) }
}