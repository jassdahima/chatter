package com.example.chatter.feature.auth.signout

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignOutViewModel : ViewModel() {
    private val _state = MutableStateFlow<SignOutState>(SignOutState.Nothing)
    val state = _state.asStateFlow()


    fun signOut() {
        _state.value = SignOutState.Loading
        FirebaseAuth.getInstance().signOut()
                    _state.value = SignOutState.Success
                }
            }




sealed class SignOutState{
    object Nothing : SignOutState()
    object Loading : SignOutState()
    object Success : SignOutState()
    object Error : SignOutState()
}
