package com.zhuli.roombooklist

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhuli.roombooklist.data.Book
import com.zhuli.roombooklist.databinding.ActivityBookListBinding
import com.zhuli.roombooklist.ui.BookListAdapter
import com.zhuli.roombooklist.ui.BookViewModel

class BookListActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityBookListBinding
    
    private val bookViewModel: BookViewModel by viewModels {
        BookViewModel.BookViewModelFactory((application as BookApplication).repository)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Setup RecyclerView
        val adapter = BookListAdapter { book ->
            // Handle book item click
            Toast.makeText(this, "Selected: ${book.title}", Toast.LENGTH_SHORT).show()
        }
        
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        
        // Observe LiveData
        bookViewModel.allBooks.observe(this) { books ->
            // Update RecyclerView
            adapter.submitList(books)
        }
        
        // Setup FAB click event
        binding.fab.setOnClickListener {
            // Add sample book
            val newBook = Book(
                title = "New Book ${System.currentTimeMillis()}",
                author = "Unknown Author",
                year = 2023
            )
            bookViewModel.insert(newBook)
        }
    }
}