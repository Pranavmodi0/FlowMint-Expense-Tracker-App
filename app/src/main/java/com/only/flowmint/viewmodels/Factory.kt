package com.only.flowmint.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VM::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return f() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }