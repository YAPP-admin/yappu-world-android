package com.yapp.core.designsystem.extension

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color

/**
 * Composable 에 clickable 을 설정해주는 [Modifier]
 *
 * @param rippleEnabled Ripple 여부 설정
 * @param rippleColor 표시된 Ripple Color
 * @param runIf clickable 이 발생하는 조건
 * @param singleClick 더블 클릭 방지 여부
 * @param onClick 컴포넌트가 클릭됐을 때 실행할 람다식
 *
 * @return clickable 이 적용된 [Modifier]
 */
@OptIn(ExperimentalFoundationApi::class)
fun Modifier.yappClickable(
    rippleEnabled: Boolean = true,
    rippleColor: Color? = null,
    runIf: Boolean = true,
    singleClick: Boolean = true,
    onLongClick: (() -> Unit)? = null,
    onClick: (() -> Unit)?,
): Modifier {
    return if (runIf) {
        this.composed {
            val multipleEventsCutter = remember { MultipleEventsCutter.get() }

            combinedClickable(
                onClick = {
                    onClick?.let {
                        if (singleClick) {
                            multipleEventsCutter.processEvent {
                                it()
                            }
                        } else {
                            it()
                        }
                    }
                },
                onLongClick = onLongClick,
                indication = ripple(
                    color = rippleColor ?: Color.Unspecified,
                ).takeIf { rippleEnabled },
                interactionSource = remember { MutableInteractionSource() },
            )
        }
    } else {
        // 조건이 false이면 기존 Modifier 그대로 돌려줌
        this
    }
}

/**
 * 클릭이 지연될 시간을 정의하는 변수
 */
private const val CLICK_EVENT_DELAY_TIME: Long = 300L

internal interface MultipleEventsCutter {
    fun processEvent(event: () -> Unit)

    /**
     * 팩토리 메서드를 통한 인스턴스 생성을 위한 Companion 객체
     */
    companion object
}

internal fun MultipleEventsCutter.Companion.get(): MultipleEventsCutter =
    MultipleEventsCutterImpl()

private class MultipleEventsCutterImpl : MultipleEventsCutter {
    private val now: Long
        get() = System.currentTimeMillis()

    private var lastEventTimeMs: Long = 0

    override fun processEvent(event: () -> Unit) {
        if (now - lastEventTimeMs >= CLICK_EVENT_DELAY_TIME) {
            event.invoke()
        }
        lastEventTimeMs = now
    }
}