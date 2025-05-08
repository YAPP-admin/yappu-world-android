package com.yapp.feature.home.component

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.yapp.core.designsystem.theme.YappTheme
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
internal fun Indicators(
    modifier: Modifier = Modifier,
    currentPage: Int,
    itemCount: Int,
    onPageSelect: (Int) -> Unit,
    visibleDotCount: Int = 6,
    dotSize: Dp = 8.dp,
    selectedDotSize: Dp = 8.dp,
    dotSpacing: Dp = 8.dp,
    activeColor: Color = YappTheme.colorScheme.staticWhite,
    inactiveColor: Color = YappTheme.colorScheme.staticWhite.copy(alpha = 0.16f)
) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current
    val haptics = LocalHapticFeedback.current


    val dotSpacingPx = with(density) { (dotSize + dotSpacing).toPx() }
    val scrollTarget = remember(currentPage, itemCount) {
        when {
            itemCount <= visibleDotCount -> 0
            currentPage < visibleDotCount / 2 -> 0
            currentPage > itemCount - (visibleDotCount / 2) - 1 -> itemCount - visibleDotCount
            else -> currentPage - (visibleDotCount / 2)
        }
    }
    var dragStartPage by remember { mutableIntStateOf(currentPage) }
    var dragStartX by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(scrollTarget) {
        coroutineScope.launch {
            lazyListState.animateScrollToItem(index = scrollTarget)
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            state = lazyListState,
            userScrollEnabled = false,
            modifier = Modifier
                .pointerInput(itemCount, currentPage) {
                    detectDragGesturesAfterLongPress(
                        onDragStart = { offset ->
                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                            dragStartPage = currentPage
                            dragStartX = offset.x
                        },
                        onDrag = { change, _ ->
                            val dragDistance = change.position.x - dragStartX
                            val pageOffset = (dragDistance / dotSpacingPx).toInt()
                            val newPage = (dragStartPage + pageOffset).coerceIn(0, itemCount - 1)
                            if (newPage != currentPage) {
                                haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                onPageSelect(newPage)
                            }
                        }
                    )
                }
                .widthIn(max = (visibleDotCount * (dotSize + dotSpacing)).coerceAtLeast(60.dp)),
            horizontalArrangement = Arrangement.spacedBy(dotSpacing),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(itemCount) { i ->
                val scaleFactor = 1f - (0.1f * abs(i - currentPage)).coerceAtMost(0.4f)
                val color = if (i == currentPage) activeColor else inactiveColor
                Box(
                    modifier = Modifier
                        .size(if (i == currentPage) selectedDotSize else dotSize)
                        .graphicsLayer {
                            scaleX = scaleFactor
                            scaleY = scaleFactor
                        }
                        .drawBehind {
                            drawCircle(color)
                        }
                )
            }
        }
    }
}
