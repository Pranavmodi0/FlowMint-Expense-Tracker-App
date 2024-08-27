package com.only.flowmint.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashScreenViewModel: ViewModel() {

    private val _keepSplashScreenOnScreen = MutableStateFlow(true)
    val keepSplashScreenOnScreen = _keepSplashScreenOnScreen.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000L)
            _keepSplashScreenOnScreen.value = true
        }
    }
}