package com.zhuli.roombooklist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.zhuli.roombooklist.data.Book
import com.zhuli.roombooklist.data.BookRepository
import com.zhuli.roombooklist.network.BookNetworkRepository
import kotlinx.coroutines.launch

class BookViewModel(private val repository: BookRepository) : ViewModel() {
    
    // Convert Flow to LiveData
    val allBooks: LiveData<List<Book>> = repository.allBooks.asLiveData()
    
    private val _isNetworkAvailable = MutableLiveData<Boolean>()
    val isNetworkAvailable: LiveData<Boolean> = _isNetworkAvailable
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    fun refreshFromNetwork() = viewModelScope.launch {
        _isLoading.value = true
        val success = repository.refreshBooksFromNetwork()
        _isNetworkAvailable.value = success
        _isLoading.value = false
    }
    
    // Insert book
    fun insert(book: Book) = viewModelScope.launch {
        repository.insert(book)
    }
    
    // Update book
    fun update(book: Book) = viewModelScope.launch {
        repository.update(book)
    }
    
    // Delete book
    fun delete(book: Book) = viewModelScope.launch {
        repository.delete(book)
    }
    
    // ViewModel factory
    class BookViewModelFactory(
        private val repository: BookRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return BookViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}