package com.yapp.core.ui.extension

import androidx.compose.ui.platform.UriHandler
import com.yapp.core.common.android.record
import timber.log.Timber

fun UriHandler.safeOpenUri(rawUrl: String) {
    val url = if (!rawUrl.startsWith("http://") && !rawUrl.startsWith("https://")) {
        "https://$rawUrl"
    } else {
        rawUrl
    }

    try {
        openUri(url)
    } catch (e: Exception) {
        e.record()
        Timber.e(e, "Failed to open URI: $url")
    }
}