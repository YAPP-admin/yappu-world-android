package com.yapp.app.official

import android.content.pm.PackageManager
import android.os.Build
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.yapp.app.official.ui.YappApp
import com.yapp.app.official.ui.rememberNavigator
import com.yapp.core.designsystem.component.alert.YappAlertDialog
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.dataapi.OperationsRepository
import com.yapp.domain.UpdateDeviceAlarmUseCase
import com.yapp.domain.runCatchingIgnoreCancelled
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.core.net.toUri
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.yapp.domain.CheckLoginStatusUseCase
import com.yapp.feature.home.navigation.HomeRoute


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var updateDeviceAlarmUseCase: UpdateDeviceAlarmUseCase

    @Inject
    lateinit var operationsRepository: OperationsRepository

    @Inject
    lateinit var checkLoginStatusUseCase: CheckLoginStatusUseCase

    private var showForceUpdateDialog by mutableStateOf(false)

    private val scope = MainScope()

    private var showSplashScreen by mutableStateOf(true)

    private val requestPermissionLauncher = registerForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        callback = { }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            showSplashScreen
        }

        enableEdgeToEdge()
        setContent {
            val navigator = rememberNavigator()

            LaunchedEffect(Unit) {
                if (checkLoginStatusUseCase()) {
                    navigator.startDestination = HomeRoute
                }
                showSplashScreen = false
            }

            LaunchedEffect(Unit) {
                runCatchingIgnoreCancelled {
                    showForceUpdateDialog = operationsRepository.isForceUpdateRequired()
                }
            }

            YappTheme {
                if (showSplashScreen.not()) {
                    YappApp(navigator)
                }

                if (showForceUpdateDialog) {
                    ForceUpdateDialog()
                }
            }
        }
    }

    @Composable
    private fun ForceUpdateDialog() {
        YappAlertDialog(
            onDismissRequest = {},
            title = stringResource(R.string.main_activity_force_update_dialog_title),
            body = stringResource(R.string.main_activity_force_update_dialog_body),
            recommendActionButtonText = stringResource(R.string.main_activity_force_update_dialog_button_text),
            onRecommendActionButtonClick = {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = "market://details?id=${packageName}".toUri()
                    setPackage("com.android.vending")
                }
                startActivity(intent)
            }
        )
    }

    override fun onResume() {
        super.onResume()

        scope.launch {
            updateDeviceAlarmUseCase()
        }
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return
        }

        val hasNotificationPermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasNotificationPermission) {
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
