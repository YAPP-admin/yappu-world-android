package com.yapp.feature.session

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapView
import com.naver.maps.map.overlay.Marker

@Composable
fun SessionNaverMap(
    modifier: Modifier = Modifier,
    center: LatLng,
    onMapReady: ((com.naver.maps.map.NaverMap) -> Unit)? = null,
) {
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
                        map = naverMap
                    }
                    onMapReady?.invoke(naverMap)
                }
            }
        }
    )
}

