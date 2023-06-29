package com.example.jetsubmission.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetsubmission.data.BookRepository
import com.example.jetsubmission.ui.screen.detail.DetailBookViewModel
import com.example.jetsubmission.ui.screen.favorite.FavoriteViewModel
import com.example.jetsubmission.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: BookRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailBookViewModel::class.java)) {
            return DetailBookViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}