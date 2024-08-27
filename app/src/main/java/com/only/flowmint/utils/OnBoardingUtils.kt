package com.only.flowmint.utils

import android.content.Context

class OnBoardingUtils(private val context: Context) {

    fun isOnboarding(): Boolean {
        return context.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
            .getBoolean("completed", false)
    }

    fun setOnboarding() {
        context.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
            .edit()
            .putBoolean("completed", true)
            .apply()
    }
}