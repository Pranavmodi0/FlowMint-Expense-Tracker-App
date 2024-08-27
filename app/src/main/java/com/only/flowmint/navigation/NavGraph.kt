package com.only.flowmint.navigation

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.only.flowmint.pages.BiometricsAuth
import com.only.flowmint.pages.Login
import com.only.flowmint.viewmodels.AuthViewModel
import kotlinx.coroutines.launch

const val WED_ID = "132764102923-ddjuog19p1rbl0fnrcu65fvh49lhbnlh.apps.googleusercontent.com"
private lateinit var auth: FirebaseAuth
private lateinit var user: FirebaseUser

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun NavGraph(navController: NavHostController) {

    val vm : AuthViewModel = viewModel()
    val uiState by vm.uiState.collectAsState()

    auth = Firebase.auth

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val credentialManager = CredentialManager.create(context)

    LaunchedEffect(Unit) {
        vm.refreshAuth()
    }

    val startDestinations = remember {
        mutableStateOf(
            if (uiState.categories.any { it.email.isNotEmpty() }) {
                Routes.Biometrics.routes} else {
                Routes.SignIn.routes
            }
        )
    }

    val startDestination = startDestinations.value

    var loading by remember {
        mutableStateOf(false)
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, tween(700)) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up, tween(700)) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) }
    ) {
        composable(
            Routes.SignIn.routes,
        ) {
            Login(
                loading = loading,
                onClick = {
                    loading = true
                    val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                        .setFilterByAuthorizedAccounts(false)
                        .setServerClientId(WED_ID)
                    .build()

                    val request = GetCredentialRequest.Builder()
                        .addCredentialOption(googleIdOption)
                        .build()

                    scope.launch {
                        try {
                            val result = credentialManager.getCredential(
                                request = request,
                                context = context,
                            )
                            val credential = result.credential
                            val googleIdTokenCredential = GoogleIdTokenCredential
                                .createFrom(credential.data)
                            val googleIdToken = googleIdTokenCredential.idToken

                            val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)


                            auth.signInWithCredential(firebaseCredential)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        scope.launch {
                                            user = auth.currentUser!!
                                            vm.setEmail(user.email.toString())
                                            vm.setProfile(user.photoUrl.toString())
                                            loading = false
                                            vm.createAuth()
                                            navController.popBackStack()
                                            navController.navigate(Routes.Biometrics.routes)
                                        }
                                    }
                                }

                        } catch (e: Exception) {
                            loading = false
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT)
                                .show()
                            e.printStackTrace()
                        }
                    }
                }
            )
        }
        composable(
            Routes.Biometrics.routes,
        ) {
            BiometricsAuth(navController = navController)
        }
        composable(Routes.BottomNav.routes) {
            BottomNav()
        }
    }
}
