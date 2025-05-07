package com.zhuli.roombooklist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun bookDao(): BookDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "book_database"
                )
                .fallbackToDestructiveMigration()
                .addCallback(BookDatabaseCallback(scope))
                .build()
                INSTANCE = instance
                instance
            }
        }
        
        private class BookDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.bookDao())
                    }
                }
            }
            
            suspend fun populateDatabase(bookDao: BookDao) {
                // delete all contents
                bookDao.deleteAllBooks()
                
                // add sample books
                // TODO: JUST For Mock！！！ replace with your own data or remove this method
                val sampleBooks = listOf(
                    Book(title = "Android Programming: The Big Nerd Ranch Guide", author = "Bill Phillips", year = 2021),
                    Book(title = "Kotlin in Action", author = "Dmitry Jemerov", year = 2017),
                    Book(title = "Understanding the JVM", author = "Zhou Zhiming", year = 2019),
                    Book(title = "Clean Code", author = "Robert C. Martin", year = 2008),
                    Book(title = "Design Patterns", author = "Erich Gamma", year = 1994)
                )
                bookDao.insertBooks(sampleBooks)
            }
        }
    }
}