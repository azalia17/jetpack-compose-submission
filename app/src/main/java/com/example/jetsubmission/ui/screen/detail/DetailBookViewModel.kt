package com.example.jetsubmission.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetsubmission.data.BookRepository
import com.example.jetsubmission.model.Book
import com.example.jetsubmission.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailBookViewModel(private val repository: BookRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Book>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Book>> get() = _uiState

    fun getBookById(bookId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getBookById(bookId))
        }
    }

    fun updateBook(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateBook(id, !newState)
            .collect { isUpdated ->
                if (isUpdated) getBookById(id)
            }
    }
}