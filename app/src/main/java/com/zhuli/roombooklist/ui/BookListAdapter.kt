package com.zhuli.roombooklist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zhuli.roombooklist.R
import com.zhuli.roombooklist.data.Book

class BookListAdapter(private val onItemClickListener: (Book) -> Unit) : 
    ListAdapter<Book, BookListAdapter.BookViewHolder>(BookDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        holder.bind(book)
        holder.itemView.setOnClickListener {
            onItemClickListener(book)
        }
    }
    
    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        private val authorTextView: TextView = itemView.findViewById(R.id.textViewAuthor)
        private val yearTextView: TextView = itemView.findViewById(R.id.textViewYear)
        
        fun bind(book: Book) {
            titleTextView.text = book.title
            authorTextView.text = book.author
            yearTextView.text = book.year.toString()
        }
    }
    
    class BookDiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }
}