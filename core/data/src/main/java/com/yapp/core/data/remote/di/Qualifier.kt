package com.yapp.core.data.remote.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class AuthOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class AuthRetrofit
