package com.example.chatter.di

import com.example.chatter.feature.auth.signin.SignInViewModel
import com.example.chatter.feature.auth.signout.SignOutViewModel
import com.example.chatter.feature.auth.signup.SignUpViewModel
import com.example.chatter.feature.chat.ChatViewModel
import com.example.chatter.feature.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {


    viewModel { SignInViewModel() }
    viewModel { SignUpViewModel() }
    viewModel { HomeViewModel() }
    viewModel { ChatViewModel() }
    viewModel { SignOutViewModel() }

}