package com.yapp.core.ui.constant

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

object Url {
    const val TERMS = "https://yapp-workspace.notion.site/48f4eb2ffdd94740979e8a3b37ca260d"
    const val PRIVACY_POLICY = "https://yapp-workspace.notion.site/fc24f8ba29c34f9eb30eb945c621c1ca"

    fun moveToUrl(context : Context, url : String){
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        }catch (e :Exception ){
            Toast.makeText(context,"브라우저를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}