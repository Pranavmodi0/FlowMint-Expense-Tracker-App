package com.only.flowmint.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.only.flowmint.MyApp
import com.only.flowmint.data.AuthState
import com.only.flowmint.data.Authentication
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(AuthState())
    val uiState: StateFlow<AuthState> = _uiState.asStateFlow()

    private val db = MyApp.db

    init {
        _uiState.update { currentState ->
            currentState.copy(
                categories = db.query<Authentication>().find()
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            db.query<Authentication>().asFlow().collect{ changes->
                _uiState.update { currentState ->
                    currentState.copy(
                        categories = changes.list
                    )
                }
            }
        }
    }

    fun setEmail(name: String) {
        _uiState.update { currentState ->
            currentState.copy(
                authEmail = name,
            )
        }
    }

    fun setProfile(profile: String) {
        _uiState.update { currentState ->
            currentState.copy(
                authProfile = profile,
            )
        }
    }

    fun createAuth() {
        viewModelScope.launch(Dispatchers.IO) {
            db.write {
                this.copyToRealm(
                    Authentication(
                        _uiState.value.authEmail,
                        _uiState.value.authProfile
                    )
                )
            }
        }
    }

    fun deleteEmail(email: Authentication) {
        viewModelScope.launch(Dispatchers.IO) {
            db.write {
                val emailToDelete =
                    this.query<Authentication>("id == $0", email.id).find().first()
                delete(emailToDelete)
            }
        }
    }

    fun refreshAuth() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { currentState ->
                currentState.copy(
                    categories = db.query<Authentication>().find()
                )
            }
        }
    }
}

