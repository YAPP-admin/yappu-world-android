package com.yapp.app.official

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.yapp.app.official.ui.YappApp
import com.yapp.app.official.ui.rememberNavigator
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.domain.UpdateDeviceAlarmUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var updateDeviceAlarmUseCase: UpdateDeviceAlarmUseCase

    private val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navigator = rememberNavigator()
            YappTheme {
                YappApp(navigator)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        scope.launch {
            updateDeviceAlarmUseCase()
        }
    }
}

