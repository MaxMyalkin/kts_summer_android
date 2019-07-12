package ru.ktsstudio.myapplication.data.models

import com.google.gson.annotations.SerializedName

enum class SortOrder {
    @SerializedName("desc")
    DESCENDING,
    @SerializedName("asc")
    ASCENDING
}