package dev.drtheo.mes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.drtheo.mes.ui.screens.AuthUiState
import dev.drtheo.mes.ui.screens.DnevnikViewModel
import dev.drtheo.mes.ui.theme.DnevnikTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DnevnikTheme {
                val dnevnikViewModel: DnevnikViewModel = viewModel(factory = DnevnikViewModel.Factory)

                when (dnevnikViewModel.authUiState) {
                    is AuthUiState.Unauthenticated -> LoginActivity(dnevnikViewModel)
                    is AuthUiState.Authenticated -> HomeActivity(dnevnikViewModel)
                }
            }
        }
    }
}