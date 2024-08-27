package com.only.flowmint.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.only.flowmint.R
import com.only.flowmint.pages.Add
import com.only.flowmint.pages.BiometricsAuth
import com.only.flowmint.pages.Category
import com.only.flowmint.pages.Expenses
import com.only.flowmint.pages.Login
import com.only.flowmint.pages.Report
import com.only.flowmint.pages.Settings
import com.only.flowmint.ui.theme.RealmAppTheme
import com.only.flowmint.ui.theme.TextColor
import com.only.flowmint.ui.theme.Top
import com.only.flowmint.viewmodels.AuthViewModel
import kotlinx.coroutines.launch

private lateinit var auth: FirebaseAuth

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun BottomNav() {

    auth = Firebase.auth
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val credentialManager = CredentialManager.create(context)

    val vm: AuthViewModel = viewModel()
    val uiState by vm.uiState.collectAsState()

    val list = listOf(
        BottomNavigationItem(
            title = "Expenses",
            selectedIcon = R.drawable.account,
            Routes.Expenses.routes
        ),
        BottomNavigationItem(
            title = "Report",
            selectedIcon = R.drawable.expense,
            Routes.Report.routes
        ),
        BottomNavigationItem(
            title = "Add",
            selectedIcon = R.drawable.add,
            Routes.Add.routes
        ),
        BottomNavigationItem(
            title = "Setting",
            selectedIcon = R.drawable.setting,
            Routes.Settings.routes
        )
    )

    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()

    var showBottomBar by rememberSaveable {
        mutableStateOf(true)
    }

    showBottomBar = when (backStackEntry?.destination?.route) {
        Routes.Category.routes -> false
        Routes.SignIn.routes -> false
        else -> true
    }
    RealmAppTheme {

        Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    NavigationBar(
                        containerColor = TextColor,
                        modifier = Modifier.clip(RoundedCornerShape(40.dp))
                    ) {
                        list.forEachIndexed { _, item ->
                            NavigationBarItem(
                                selected = backStackEntry?.destination?.route?.startsWith(item.route)
                                    ?: false,
                                onClick = {
                                    navController.navigate(item.route)
                                },
                                icon = {
                                    Icon(
                                        painter = painterResource(id = item.selectedIcon),
                                        contentDescription = item.title,
                                        modifier = Modifier.size(25.dp)
                                    )
                                }
                            )
                        }
                    }
                }
            },
            content = { padding ->
                val startDestinations = remember {
                    mutableStateOf(
                        if (uiState.categories.any { it.email.isNotEmpty() }) {
                            Routes.Expenses.routes
                        } else {
                            Routes.SignIn.routes
                        }
                    )
                }

                val startDestination = startDestinations.value

                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    modifier = Modifier.padding(padding),
                    enterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Down,
                            tween(700)
                        )
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Down,
                            tween(700)
                        )
                    },
                    popEnterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.End,
                            tween(700)
                        )
                    },
                    popExitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.End,
                            tween(700)
                        )
                    }

                ) {
                    composable(Routes.SignIn.routes) {
                        Login({})
                    }
                    composable(Routes.Biometrics.routes) {
                        BiometricsAuth(navController = navController)
                    }
                    composable(Routes.Expenses.routes) {
                        Expenses()
                    }
                    composable(Routes.Report.routes) {
                        Report()
                    }
                    composable(Routes.Add.routes) {
                        Add()
                    }
                    composable(Routes.Settings.routes) {
                        Settings(navController = navController, onClick = {
                            scope.launch {
//                                auth.signOut()
//                                credentialManager.clearCredentialState(
//                                    ClearCredentialStateRequest()
//                                )
                                for (category in uiState.categories) {
                                    vm.deleteEmail(category)
                                }
                            }
                            navController.popBackStack()
                            navController.navigate(Routes.SignIn.routes)
                        })
                    }
                    composable(Routes.Category.routes) {
                        Category(navController = navController)
                    }
                }
            }
        )
    }
}