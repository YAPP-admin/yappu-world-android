package com.yapp.core.ui.extension

import androidx.compose.ui.platform.UriHandler
import com.yapp.core.common.android.record
import timber.log.Timber

fun UriHandler.safeOpenUri(rawUrl: String) {
    val url = when {
        rawUrl.startsWith("https://") -> rawUrl
        rawUrl.startsWith("http://") -> "https://" + rawUrl.removePrefix("http://")
        else -> "https://$rawUrl"
    }

    try {
        openUri(url)
    } catch (e: Exception) {
        e.record()
        Timber.e(e, "Failed to open URI: $url")
    }
}