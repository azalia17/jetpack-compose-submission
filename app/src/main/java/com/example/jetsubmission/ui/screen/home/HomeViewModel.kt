package com.example.jetsubmission.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetsubmission.data.BookRepository
import com.example.jetsubmission.model.Book
import com.example.jetsubmission.model.BooksData.books
import com.example.jetsubmission.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: BookRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Book>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Book>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun getAllBooks() {
        viewModelScope.launch {
            repository.getAllBooks()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { books ->
                    _uiState.value = UiState.Success(books)
                }
        }
    }

    fun search(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        repository.searchBook(_query.value)
            .catch { _uiState.value = UiState.Error(it.message.toString()) }
            .collect { _uiState.value = UiState.Success(it) }
    }
}