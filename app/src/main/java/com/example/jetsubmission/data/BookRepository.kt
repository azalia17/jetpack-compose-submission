package com.example.jetsubmission.data

import com.example.jetsubmission.model.Book
import com.example.jetsubmission.model.BooksData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class BookRepository {
    private val book = mutableListOf<Book>()

    init {
        if (book.isEmpty()) {
            book.addAll(BooksData.books)
//            BooksData.books.forEach {
//                book.addAll()
//            }
        }
    }

    fun getAllBooks(): Flow<List<Book>> {
        return flowOf(book)
    }

    fun getBookById(id: Int): Book {
        return book.first {
            it.id == id
        }
    }

    fun getFavoriteBook(): Flow<List<Book>> {
        return flowOf(book.filter { it.isFavorite })
    }

    fun searchBook(query: String): Flow<List<Book>> {
        return flowOf(book.filter { it.title.contains(query, ignoreCase = true) })
    }

    fun updateBook(id: Int, newState: Boolean): Flow<Boolean> {
        val index = book.indexOfFirst { it.id == id }
        val result = if (index >= 0) {
            val bookData = book[index]
            book[index] = bookData.copy(isFavorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: BookRepository? = null

        fun getInstance(): BookRepository =
            instance ?: synchronized(this) {
                BookRepository().apply {
                    instance = this
                }
            }
    }
}