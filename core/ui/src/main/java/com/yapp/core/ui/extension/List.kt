package com.yapp.core.ui.extension

fun <T> List<T>.replace(index: Int, transform: (T) -> T): List<T> {
    if (index !in indices) return this
    return toMutableList().apply { this[index] = transform(this[index]) }
}