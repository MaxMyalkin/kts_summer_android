package ru.ktsstudio.myapplication.data.models

import com.google.gson.annotations.SerializedName

enum class ReactionType {
    @SerializedName("+1")
    LIKE,
    @SerializedName("-1")
    DISLIKE,
    @SerializedName("laugh")
    SMILE,
    @SerializedName("confused")
    CONFUSED,
    @SerializedName("heart")
    HEART
}