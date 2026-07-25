package com.example.bookapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookapp.data.AppDatabase
import com.example.bookapp.data.seedDatabaseIfEmpty
import com.example.bookapp.ui.screens.*

private const val ROUTE_LOGIN = "login"
private const val ROUTE_MAIN_MENU = "main_menu"
private const val ROUTE_ABOUT = "about"
private const val ROUTE_SETTINGS = "settings"
private const val ROUTE_VERSION = "version"
private const val ROUTE_FIELDS = "fields"
private const val ROUTE_TAZIEHS = "taziehs/{fieldId}/{fieldTitle}"
private const val ROUTE_ROLES = "roles/{taziehId}/{taziehTitle}"
private const val ROUTE_SECTIONS = "sections/{roleId}/{roleTitle}"
private const val ROUTE_TEXT = "text/{sectionId}"

@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val navController: NavHostController = rememberNavController()

    // بارگذاری اولیه داده‌ها از assets/sample_data.json در صورت خالی بودن دیتابیس
    LaunchedEffect(Unit) {
        seedDatabaseIfEmpty(context, db)
    }

    NavHost(navController = navController, startDestination = ROUTE_LOGIN) {

        composable(ROUTE_LOGIN) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(ROUTE_MAIN_MENU) {
                    popUpTo(ROUTE_LOGIN) { inclusive = true }
                }
            })
        }

        // منوی اصلی: لیست تعزیه‌ها / درباره برنامه / تنظیمات / ورژن
        composable(ROUTE_MAIN_MENU) {
            MainMenuScreen(
                onOpenTaziehList = { navController.navigate(ROUTE_FIELDS) },
                onOpenAbout = { navController.navigate(ROUTE_ABOUT) },
                onOpenSettings = { navController.navigate(ROUTE_SETTINGS) },
                onOpenVersion = { navController.navigate(ROUTE_VERSION) }
            )
        }

        composable(ROUTE_ABOUT) { AboutScreen(onBack = { navController.popBackStack() }) }
        composable(ROUTE_SETTINGS) { SettingsScreen(onBack = { navController.popBackStack() }) }
        composable(ROUTE_VERSION) { VersionScreen(onBack = { navController.popBackStack() }) }

        // سطح ۱: فهرست زمینه‌ها
        composable(ROUTE_FIELDS) {
            var items by remember { mutableStateOf(listOf<ListItemData>()) }
            LaunchedEffect(Unit) {
                items = db.fieldDao().getAll().map { ListItemData(it.id, it.title) }
            }
            GenericListScreen(
                screenTitle = "زمینه‌ها",
                items = items,
                onItemClick = { navController.navigate("taziehs/${it.id}/${it.title}") },
                onBack = { navController.popBackStack() }
            )
        }

        // سطح ۲: فهرست تعزیه‌ها
        composable(ROUTE_TAZIEHS) { backStackEntry ->
            val fieldId = backStackEntry.arguments?.getString("fieldId")?.toLongOrNull() ?: 0L
            val fieldTitle = backStackEntry.arguments?.getString("fieldTitle") ?: ""
            var items by remember { mutableStateOf(listOf<ListItemData>()) }
            LaunchedEffect(fieldId) {
                items = db.taziehDao().getByField(fieldId).map { ListItemData(it.id, it.title) }
            }
            GenericListScreen(
                screenTitle = fieldTitle,
                items = items,
                onItemClick = { navController.navigate("roles/${it.id}/${it.title}") },
                onBack = { navController.popBackStack() }
            )
        }

        // سطح ۳: فهرست نقش‌ها
        composable(ROUTE_ROLES) { backStackEntry ->
            val taziehId = backStackEntry.arguments?.getString("taziehId")?.toLongOrNull() ?: 0L
            val taziehTitle = backStackEntry.arguments?.getString("taziehTitle") ?: ""
            var items by remember { mutableStateOf(listOf<ListItemData>()) }
            LaunchedEffect(taziehId) {
                items = db.roleDao().getByTazieh(taziehId).map { ListItemData(it.id, it.title) }
            }
            GenericListScreen(
                screenTitle = taziehTitle,
                items = items,
                onItemClick = { navController.navigate("sections/${it.id}/${it.title}") },
                onBack = { navController.popBackStack() }
            )
        }

        // سطح ۴: فهرست بخش‌ها
        composable(ROUTE_SECTIONS) { backStackEntry ->
            val roleId = backStackEntry.arguments?.getString("roleId")?.toLongOrNull() ?: 0L
            val roleTitle = backStackEntry.arguments?.getString("roleTitle") ?: ""
            var items by remember { mutableStateOf(listOf<ListItemData>()) }
            LaunchedEffect(roleId) {
                items = db.sectionDao().getByRole(roleId).map { ListItemData(it.id, it.title) }
            }
            GenericListScreen(
                screenTitle = roleTitle,
                items = items,
                onItemClick = { navController.navigate("text/${it.id}") },
                onBack = { navController.popBackStack() }
            )
        }

        // سطح ۵: نمایش متن اشعار بخش
        composable(ROUTE_TEXT) { backStackEntry ->
            val sectionId = backStackEntry.arguments?.getString("sectionId")?.toLongOrNull() ?: 0L
            var title by remember { mutableStateOf("") }
            var content by remember { mutableStateOf("") }
            LaunchedEffect(sectionId) {
                val section = db.sectionDao().getById(sectionId)
                title = section.title
                content = section.content
            }
            TextScreen(title = title, content = content, onBack = { navController.popBackStack() })
        }
    }
}
