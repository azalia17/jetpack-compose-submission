package com.example.jetsubmission.di

import com.example.jetsubmission.data.BookRepository

object Injection {
    fun provideRepository(): BookRepository {
        return BookRepository.getInstance()
    }
}