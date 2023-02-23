package com.example.blogging.ui.screens.authScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.blogging.ui.navigation.MainNavGraph
import com.example.blogging.ui.screens.authScreen.components.google.ButtonAuthGoogle
import com.example.blogging.ui.screens.states.SimpleScreenState
import com.example.blogging.ui.screens.states.rememberSimpleScreenState
import com.example.blogging.ui.viewmodel.AuthViewModel
import com.google.firebase.auth.AuthCredential
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

     AuthScreen(authViewModel.isProcessing , authScreenState.scaffoldState  , authViewModel::authWithCredential)

}

@Composable
fun AuthScreen(
    isAuthenticated: Boolean,
    scaffoldState: ScaffoldState,
    actionAuthWithCredential: (AuthCredential) -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            //ContainerLogo()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isAuthenticated) {
                    CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
                } else {
                    ButtonsAuth(authWithCredential = actionAuthWithCredential)
                }
            }
        }
    }
}

@Composable
private fun ButtonsAuth(
    modifier: Modifier = Modifier,
    authWithCredential: (AuthCredential) -> Unit,
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // * text disclaimer
        Text(
            "stringResource(R.string.text_disclaimer)",
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .width(120.dp)
                .align(Alignment.CenterHorizontally)
        )
        // * button auth with google
        ButtonAuthGoogle(
            modifier = Modifier.width(250.dp),
            actionBeforeAuth = authWithCredential,
        )

    }
}