package com.hw12

sealed class State {
    data object Waiting: State()
    data object Ready: State()
    data object Search: State()
    data object Success: State()
}