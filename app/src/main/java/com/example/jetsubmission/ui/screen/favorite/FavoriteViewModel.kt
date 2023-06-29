package com.example.jetsubmission.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetsubmission.data.BookRepository
import com.example.jetsubmission.model.Book
import com.example.jetsubmission.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: BookRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Book>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Book>>> get() = _uiState

    fun getFavoriteBook() {
        viewModelScope.launch {
            repository.getFavoriteBook()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun updateBook(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateBook(id, !newState)
        getFavoriteBook()
    }
}