package com.yapp.official.ui.user

sealed interface UserAction {
    data object AddUser : UserAction
    data object RemoveUser : UserAction
}
