package com.yapp.model

data class YappJWT(
    val accessToken: String,
    val refreshToken: String,
)