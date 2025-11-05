package com.example.ebook.domain.repo

import com.example.ebook.common.BookCategoryModel
import com.example.ebook.common.BookModel
import com.example.ebook.common.ResultState
import kotlinx.coroutines.flow.Flow

interface AllBookRepo {
    fun getAllBooks(): Flow<ResultState<List<BookModel>>>
    fun getAllCategory(): Flow<ResultState<List<BookCategoryModel>>>
    fun getAllBooksByCategory(category: String): Flow<ResultState<List<BookModel>>>
}