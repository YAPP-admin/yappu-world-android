package com.yapp.core.common.android

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import timber.log.Timber

fun Throwable.record() {
    recordException(RuntimeException(this))
}

private fun recordException(
    e: Throwable,
) {
    Timber.e(e)
    Firebase.crashlytics.recordException(e)
}