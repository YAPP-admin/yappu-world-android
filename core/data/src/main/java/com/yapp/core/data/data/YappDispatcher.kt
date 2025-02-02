package com.yapp.core.data.data

import javax.inject.Qualifier

@Qualifier
annotation class Dispatcher(val yappDispatcher: YappDispatchers)

enum class YappDispatchers {
    IO,
}
