package com.project.chatr.utils

sealed class ViewModelState {
    data class Error(val message: String) : ViewModelState()
    object Success : ViewModelState()
    object Loaded : ViewModelState()
}