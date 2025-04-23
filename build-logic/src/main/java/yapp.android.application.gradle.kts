import com.yapp.configureCompose
import com.yapp.configureHiltAndroid
import com.yapp.configureKotlinAndroid

plugins {
    id("com.android.application")
}

configureKotlinAndroid()
configureCompose()
configureHiltAndroid()
