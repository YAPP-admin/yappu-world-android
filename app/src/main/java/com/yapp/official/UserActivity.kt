package com.yapp.official

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yapp.official.data.UserState
import com.yapp.official.ui.theme.YappOfficialTheme
import com.yapp.official.ui.user.UserIntent
import com.yapp.official.ui.user.UserViewModel
import com.yapp.official.ui.user.UserViewState


class MainActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YappOfficialTheme {
                UserScreen(userViewModel = userViewModel)
            }

        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserScreen(userViewModel: UserViewModel) {
    val viewState by userViewModel.viewState.collectAsState()

    Scaffold(
        content = {
            Column(modifier = Modifier.padding(16.dp)) {
                Row {
                    Button(onClick = { userViewModel.processIntent(UserIntent.AddUser) }) {
                        Text(text = "사람 추가")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(onClick = { userViewModel.processIntent(UserIntent.RemoveUser) }) {
                        Text(text = "사람 제거")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn {
                    items(viewState.users) { user ->
                        UserItem(user = user)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

            }
        }
    )

}


@Composable
fun UserItem(user: UserState) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "ID: ${user.id}")
        Text(text = "Name: ${user.name}")
    }
}
