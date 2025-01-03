package com.yapp.official.ui.user

sealed interface UserIntent {
    data object AddUser : UserIntent
    data object RemoveUser : UserIntent
}
