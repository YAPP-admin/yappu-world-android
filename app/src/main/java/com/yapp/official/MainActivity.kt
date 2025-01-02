package com.yapp.official

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yapp.official.ui.theme.YappOfficialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YappOfficialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Example1Screen(modifier = Modifier.padding(innerPadding))
                    Example2Screen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Example1Screen(
    modifier: Modifier = Modifier,
    viewModel: Example1ViewModel = viewModel()
) {
    val uiState by viewModel.store.uiState.collectAsState() // TODO WithLifeCycle

    val context = LocalContext.current
    viewModel.store.sideEffects.collectWithLifecycle { effect ->
        when (effect) {
            is ExampleSideEffect.ShowMessage -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
            is ExampleSideEffect.ShowError -> {
                Toast.makeText(context, effect.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = modifier,
    ) {
        Text(text = "Current Items: ${uiState.items.size}")
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                viewModel.store.onIntent(ExampleIntent.ClickIncrementButton)
            }
        ) {
            Text("Increment")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                viewModel.store.onIntent(ExampleIntent.ClickDecrementButton)
            }
        ) {
            Text("Decrement")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val startIndex = uiState.items.size
                viewModel.store.onIntent(ExampleIntent.ClickLoadMoreButton(startIndex))
            }
        ) {
            Text("Load More Items")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoading) {
            Text("Loading...")
        }

        uiState.items.forEach { item ->
            Text(item)
        }
    }
}

@Composable
fun Example2Screen(
    modifier: Modifier = Modifier,
    viewModel: Example2ViewModel = viewModel()
) {
    val uiState by viewModel.store.uiState.collectAsState() // TODO WithLifeCycle

    val context = LocalContext.current
    viewModel.store.sideEffects.collectWithLifecycle { effect ->
        when (effect) {
            is ExampleSideEffect.ShowMessage -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
            is ExampleSideEffect.ShowError -> {
                Toast.makeText(context, effect.error, Toast.LENGTH_SHORT).show()
            }
        }

    }

    Column(
        modifier = modifier,
    ) {
        Text(text = "Current Items: ${uiState.items.size}")
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                viewModel.onClickIncrementButton()
            }
        ) {
            Text("Increment")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                viewModel.onClickDecrementButton()
            }
        ) {
            Text("Decrement")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val startIndex = uiState.items.size
                viewModel.onClickLoadMoreButton(startIndex)
            }
        ) {
            Text("Load More Items")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoading) {
            Text("Loading...")
        }

        uiState.items.forEach { item ->
            Text(item)
        }
    }
}