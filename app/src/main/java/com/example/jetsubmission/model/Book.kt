package com.example.jetsubmission.model

data class Book (
    val id: Int,
    val title: String,
    val author: String,
    val genres: String,
    val price: String,
    val detail: String,
    val pages: String,
    val photo: Int,
    var isFavorite: Boolean = false,
)