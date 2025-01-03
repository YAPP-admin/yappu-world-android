package com.yapp.official.ui.user

import androidx.lifecycle.ViewModel
import com.yapp.official.data.UserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserViewModel : ViewModel() {
    private val _viewState = MutableStateFlow(UserViewState())
    val viewState: StateFlow<UserViewState> get() = _viewState

    fun processIntent(intent: UserIntent) {
        when (intent) {
            is UserIntent.AddUser -> addUser()
            is UserIntent.RemoveUser -> removeUser()
        }
    }

    private fun addUser() {
        val newUser = UserState(id = _viewState.value.nextId, name = "Jaewon")
        _viewState.value = _viewState.value.copy(
            users = _viewState.value.users + newUser,
            nextId = _viewState.value.nextId + 1
        )
    }

    private fun removeUser() {
        if (_viewState.value.users.isNotEmpty()) {
            _viewState.value = _viewState.value.copy(
                users = _viewState.value.users.dropLast(1)
            )
        }
    }
}
