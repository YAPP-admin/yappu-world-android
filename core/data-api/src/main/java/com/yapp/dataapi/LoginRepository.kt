package com.yapp.dataapi

interface LoginRepository {
    suspend fun login(
        email : String,
        password : String
    )
}