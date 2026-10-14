package com.aegis.manager.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aegis.manager.ui.components.AegisTab
import com.aegis.manager.ui.screens.createMaster.CreateMasterScreen
import com.aegis.manager.ui.screens.detail.LoginDetailScreen
import com.aegis.manager.ui.screens.generator.GeneratorScreen
import com.aegis.manager.ui.screens.newItem.NewItemScreen
import com.aegis.manager.ui.screens.onboarding.OnboardingScreen
import com.aegis.manager.ui.screens.passwords.PasswordsScreen
import com.aegis.manager.ui.screens.category.CategoryScreen
import com.aegis.manager.ui.screens.search.SearchScreen
import com.aegis.manager.ui.screens.security.SecurityScreen
import com.aegis.manager.ui.screens.settings.SettingsScreen
import com.aegis.manager.ui.screens.unlock.UnlockScreen

object Routes {
    const val ONBOARDING = "onboarding"
    const val CREATE_MASTER = "create_master"
    const val UNLOCK = "unlock"
    const val PASSWORDS = "passwords"
    const val CATEGORY = "category/{title}"
    const val LOGIN_DETAIL = "login/{id}"
    const val NEW_ITEM = "item/new"
    const val GENERATOR = "generator"
    const val SECURITY = "security"
    const val SETTINGS = "settings"
    const val SEARCH = "search"

    fun category(title: String) = "category/$title"
    fun loginDetail(id: Long) = "login/$id"
}

@Composable
fun AegisNavHost(
    startDestination: String,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val onSelectTab: (AegisTab) -> Unit = { tab ->
        navController.navigate(tab.route) {
            popUpTo(navController.graph.startDestinationId) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                onCreateMaster = { navController.navigate(Routes.CREATE_MASTER) },
                onSignIn = { navController.navigate(Routes.UNLOCK) },
            )
        }

        composable(Routes.CREATE_MASTER) {
            CreateMasterScreen(
                onBack = { navController.popBackStack() },
                onContinue = {
                    navController.navigate(Routes.PASSWORDS) {
                        popUpTo(Routes.ONBOARDING) { inclusive = true }
                    }
                },
            )
        }

        composable(Routes.UNLOCK) {
            UnlockScreen(
                onUnlock = {
                    navController.navigate(Routes.PASSWORDS) {
                        popUpTo(0) { inclusive = true }
                    }
                },
            )
        }

        composable(Routes.PASSWORDS) {
            PasswordsScreen(
                onOpenCategory = { navController.navigate(Routes.category("Logins")) },
                onOpenLogin = { id -> navController.navigate(Routes.loginDetail(id)) },
                onAddItem = { navController.navigate(Routes.NEW_ITEM) },
                onOpenSearch = { navController.navigate(Routes.SEARCH) },
                onSelectTab = onSelectTab,
            )
        }

        composable(
            route = Routes.CATEGORY,
            arguments = listOf(navArgument("title") { type = NavType.StringType }),
        ) { entry ->
            val title = entry.arguments?.getString("title") ?: "Logins"
            CategoryScreen(
                title = title,
                onOpenLogin = { id -> navController.navigate(Routes.loginDetail(id)) },
                onAddItem = { navController.navigate(Routes.NEW_ITEM) },
                onOpenSearch = { navController.navigate(Routes.SEARCH) },
                onSelectTab = onSelectTab,
            )
        }

        composable(
            route = Routes.LOGIN_DETAIL,
            arguments = listOf(navArgument("id") { type = NavType.LongType }),
        ) { entry ->
            val id = entry.arguments?.getLong("id") ?: 0L
            LoginDetailScreen(
                itemId = id,
                onBack = { navController.popBackStack() },
            )
        }

        composable(Routes.NEW_ITEM) {
            NewItemScreen(onDone = { navController.popBackStack() })
        }

        composable(Routes.GENERATOR) {
            GeneratorScreen(onSelectTab = onSelectTab)
        }

        composable(Routes.SECURITY) {
            SecurityScreen(onSelectTab = onSelectTab)
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(onSelectTab = onSelectTab)
        }

        composable(Routes.SEARCH) {
            SearchScreen(
                onBack = { navController.popBackStack() },
                onOpenLogin = { id ->
                    navController.navigate(Routes.loginDetail(id)) {
                        popUpTo(Routes.SEARCH) { inclusive = true }
                    }
                },
                onSelectTab = onSelectTab,
            )
        }
    }
}

/** Circular add button shared by the list screens. */
@Composable
internal fun AddButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add item",
            tint = com.aegis.manager.ui.theme.AegisPrimary,
        )
    }
}

@Composable
internal fun BackButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = "Back",
            tint = com.aegis.manager.ui.theme.AegisText,
        )
    }
}
