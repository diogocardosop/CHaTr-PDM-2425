package com.project.chatr.utils

sealed class ViewModelDefinition {
    object Home : ViewModelDefinition()
    object Adding : ViewModelDefinition()
}