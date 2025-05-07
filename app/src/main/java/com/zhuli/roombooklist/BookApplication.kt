package com.zhuli.roombooklist

import android.app.Application
import com.zhuli.roombooklist.data.AppDatabase
import com.zhuli.roombooklist.data.BookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class BookApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    
    // lazy load
    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { BookRepository(database.bookDao()) }
}