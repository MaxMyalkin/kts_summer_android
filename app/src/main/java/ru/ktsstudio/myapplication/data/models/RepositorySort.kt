package ru.ktsstudio.myapplication.data.models

import com.google.gson.annotations.SerializedName

enum class RepositorySort {
    @SerializedName("stars")
    BY_STARS,
    @SerializedName("forks")
    BY_FORKS,
    @SerializedName("help-wanted-issues")
    BY_HELP_WANTED_ISSUES
}