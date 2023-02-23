package com.example.blogging.ui.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.blogging.ui.navigation.MainNavGraph
import com.example.blogging.ui.viewmodel.AuthViewModel
import com.example.blogging.ui.viewmodel.RegisterViewModel
import com.ramcosta.composedestinations.annotation.Destination


@MainNavGraph
@Destination
@Composable
fun UserDataScreen(
    authViewModel: AuthViewModel,
    registryViewModel: RegisterViewModel = hiltViewModel()
) {


}