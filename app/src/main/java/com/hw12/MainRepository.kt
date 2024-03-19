package com.hw12

import kotlinx.coroutines.delay

class MainRepository() {
    suspend fun getData(toString: String): String {
        delay(5_000)
        return "По запросу \"$toString\" ничего не найдено"
    }
}