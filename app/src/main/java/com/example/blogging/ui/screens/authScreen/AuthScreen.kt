package com.example.blogging.ui.screens.authScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.blogging.ui.navigation.MainNavGraph
import com.example.blogging.ui.screens.states.SimpleScreenState
import com.example.blogging.ui.screens.states.rememberSimpleScreenState
import com.example.blogging.ui.viewmodel.AuthViewModel
import com.ramcosta.composedestinations.annotation.Destination

@MainNavGraph(start = true)
@Destination
@Composable
fun AuthScreen(
    authViewModel: AuthViewModel,
    authScreenState: SimpleScreenState = rememberSimpleScreenState()

) {
    LaunchedEffect(key1 = Unit) {
        authViewModel.messageAuth.collect(authScreenState::showSnackMessage)
    }

   // AuthScreen(authViewModel.)

}