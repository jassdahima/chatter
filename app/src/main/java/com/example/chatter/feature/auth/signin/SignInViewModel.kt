package com.example.chatter.feature.auth.signin

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignInViewModel : ViewModel() {

   private val _state = MutableStateFlow<SignInState>(SignInState.Nothing)
    val state = _state.asStateFlow()


    fun signIn(email : String,password : String){
        _state.value = SignInState.Loading
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    task.result.user?.let {
                        _state.value = SignInState.Success
                        return@addOnCompleteListener
                    }
                    _state.value = SignInState.Error
                }
                else{
                    _state.value = SignInState.Error
                }
            }
    }













}



sealed class SignInState{
    object Nothing : SignInState()
    object Loading : SignInState()
    object Success : SignInState()
    object Error : SignInState()
}