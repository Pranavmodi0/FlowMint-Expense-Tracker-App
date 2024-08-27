package com.only.flowmint.navigation

sealed class Routes(val routes: String) {

    data object SignIn : Routes("sign_in")

    data object Biometrics : Routes("biometrics")

    data object BottomNav : Routes("bottom_nav")

    data object Expenses : Routes("expenses")

    data object Report : Routes("report")

    data object Add : Routes("add")

    data object Category : Routes("category")

    data object Settings : Routes("settings")
}