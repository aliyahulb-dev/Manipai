package com.manipai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.manipai.data.repository.AuthRepository
import com.manipai.ui.auth.AuthViewModel
import com.manipai.ui.auth.LoginScreen
import com.manipai.ui.auth.RegisterScreen
import com.manipai.ui.chat.ChatScreen
import com.manipai.ui.chat.ChatViewModel
import com.manipai.ui.chat.ConversationListScreen
import com.manipai.ui.settings.SettingsScreen
import com.manipai.ui.settings.SettingsViewModel
import com.manipai.ui.theme.ManipAITheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManipAITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val startDestination = if (authRepository.getToken() != null) "conversations" else "login"
                    AppNavigation(startDestination, authRepository)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(startDestination: String, authRepository: AuthRepository) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            val viewModel: AuthViewModel = hiltViewModel()
            LoginScreen(
                viewModel = viewModel,
                onNavigateToRegister = { navController.navigate("register") },
                onLoginSuccess = { 
                    navController.navigate("conversations") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("register") {
            val viewModel: AuthViewModel = hiltViewModel()
            RegisterScreen(
                viewModel = viewModel,
                onNavigateToLogin = { navController.popBackStack() }
            )
        }
        composable("conversations") {
            val viewModel: ChatViewModel = hiltViewModel()
            ConversationListScreen(
                viewModel = viewModel,
                onConversationClick = { id -> navController.navigate("chat/$id") },
                onSettingsClick = { navController.navigate("settings") }
            )
        }
        composable(
            route = "chat/{conversationId}",
            arguments = listOf(navArgument("conversationId") { type = NavType.StringType })
        ) { backStackEntry ->
            val viewModel: ChatViewModel = hiltViewModel()
            val conversationId = backStackEntry.arguments?.getString("conversationId") ?: ""
            ChatScreen(
                viewModel = viewModel,
                conversationId = conversationId,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("settings") {
            val viewModel: SettingsViewModel = hiltViewModel()
            SettingsScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onLogoutClick = {
                    authRepository.logout()
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
