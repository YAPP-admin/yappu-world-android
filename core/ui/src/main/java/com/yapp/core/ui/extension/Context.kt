package com.yapp.core.ui.extension

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

fun Context.openUrl(url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(this, "브라우저를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
    }
}