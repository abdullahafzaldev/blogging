package com.example.blogging.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.blogging.core.states.LoginStatus
import com.example.blogging.ui.screens.authScreen.NavGraphs
import com.example.blogging.ui.screens.authScreen.destinations.AuthScreenDestination
import com.example.blogging.ui.screens.states.rememberRootAppState
import com.example.blogging.ui.theme.BloggingTheme
import com.example.blogging.ui.viewmodel.AuthViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BloggingTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val stateAuth = authViewModel.stateUser.collectAsState()
                    val rootAppState = rememberRootAppState()
                    Log.d("TAGUser", "onCreate: ${stateAuth.value}")
                    when (stateAuth.value) {
                        LoginStatus.Authenticated.CompleteData -> null
                        LoginStatus.Authenticated.CompletingData -> null
                        LoginStatus.Authenticating -> null
                        LoginStatus.Unauthenticated -> AuthScreenDestination
                    }?.let {
                        DestinationsNavHost(
                            startRoute = it,
                            navGraph = NavGraphs.main,
                            navController = rootAppState.navController,
                            engine = rootAppState.navHostEngine,
                            dependenciesContainerBuilder = {
                                dependency(authViewModel)
                                dependency(rootAppState.rootActions)
                            }
                        )
                    }
                }
            }
        }
    }
}

