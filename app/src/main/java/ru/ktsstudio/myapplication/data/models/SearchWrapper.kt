package ru.ktsstudio.myapplication.data.models

data class SearchWrapper<T>(
    val totalCount: Int,
    val incompleteResults: Boolean,
    val items: List<T>
)