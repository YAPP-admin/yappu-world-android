package com.yapp.official.ui.user

import androidx.lifecycle.ViewModel
import com.yapp.official.data.User
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class UserViewModel : ViewModel() , ContainerHost<UserState, UserAction>{

    override val container: Container<UserState, UserAction> = container(initialState = UserState())


    fun processIntent(intent: UserAction) {
        when (intent) {
            is UserAction.AddUser -> addUser()
            is UserAction.RemoveUser -> removeUser()
        }
    }

    private fun addUser() = intent {
        val newUser = User(id = state.users.size + 1, name = "JAE WON")
        reduce {
            state.copy(users = state.users + newUser)
        }
    }

    private fun removeUser() = intent {
        if (state.users.isNotEmpty()) {
            reduce {
                state.copy(users = state.users.dropLast(1))
            }
        }
    }
}
