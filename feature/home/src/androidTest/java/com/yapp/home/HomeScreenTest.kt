package com.yapp.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.feature.home.HomeScreen
import com.yapp.feature.home.HomeState
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private fun setHomeScreenContent(state: HomeState) {
        composeTestRule.setContent {
            YappTheme {
                HomeScreen(
                    homeState = state,
                    onIntent = { }
                )
            }
        }
    }

    @Test
    fun 다음_세션이_존재하지_않는다면_출석_버튼을_노출하지_않는다() {
        // given
        val state = HomeState(
            isLoading = false,
            upcomingSession = null,
        )

        // when
        setHomeScreenContent(state)

        // then
        composeTestRule
            .onNodeWithTag("upcomingSessionCard")
            .assertDoesNotExist()
    }
}