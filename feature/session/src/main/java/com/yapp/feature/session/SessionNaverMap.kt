package com.yapp.feature.session

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapView
import com.naver.maps.map.overlay.Marker
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.designsystem.R as DesignR

@Composable
fun SessionNaverMap(
    modifier: Modifier = Modifier,
    center: LatLng,
    locationName: String? = null,
    onMapReady: ((com.naver.maps.map.NaverMap) -> Unit)? = null,
) {
    val isPreview = LocalInspectionMode.current

    if (isPreview) {
        Box(
            modifier = modifier
                .background(YappTheme.colorScheme.backgroundElevatedNormal)
                .border(
                    width = 1.dp,
                    color = YappTheme.colorScheme.lineNormalAlternative,
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = DesignR.drawable.icon_location),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = YappTheme.colorScheme.labelAssistive
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "지도 영역",
                    style = YappTheme.typography.body2ReadingBold,
                    color = YappTheme.colorScheme.labelAssistive
                )
            }
        }
        return
    }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val mapView = remember { MapView(context) }

    // Bind MapView to Lifecycle (except ON_CREATE which is handled in factory)
    DisposableEffect(lifecycleOwner, mapView) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            try { mapView.onDestroy() } catch (_: Exception) {}
        }
    }

    AndroidView(
        modifier = modifier,
        factory = {
            mapView.apply {
                // Ensure MapView is created before async map operations
                onCreate(null)
                getMapAsync { naverMap ->
                    naverMap.moveCamera(CameraUpdate.scrollTo(center))
                    Marker().apply {
                        position = center
                        captionText = locationName ?: ""
                        map = naverMap
                    }
                    onMapReady?.invoke(naverMap)
                }
            }
        }
    )
}

