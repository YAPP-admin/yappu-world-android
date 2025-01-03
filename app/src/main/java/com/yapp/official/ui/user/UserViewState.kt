package com.yapp.official.ui.user

import com.yapp.official.data.UserState

data class UserViewState(
    val users: List<UserState> = emptyList(),
    val nextId: Int = 1
)