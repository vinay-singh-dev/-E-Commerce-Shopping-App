package com.example.socialmediaapp

data class Post(
    val postId: String = "",
    val imageUrl: String = "",
    val caption: String = "",
    val userId: String = "",
    var likes: Int = 0
)
