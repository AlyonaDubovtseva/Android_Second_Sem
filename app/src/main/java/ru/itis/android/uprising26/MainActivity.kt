package ru.itis.android.uprising26

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.messaging.FirebaseMessaging
import my.study.di.AppComponentProvider
import my.study.search.di.CatsViewModelFactory
import my.study.search.ui.BreedDetailScreen
import my.study.search.ui.BreedDetailViewModel
import my.study.search.ui.CatsScreen
import my.study.search.ui.CatsViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import my.study.search.ui.custom.CustomChartScreen
import ru.itis.android.uprising26.storage.AppInfoStorage
import ru.itis.android.uprising26.ui.AppInfoDialog

class MainActivity : ComponentActivity() {

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestNotificationPermissionIfNeeded()
        logFirebaseMessagingToken()

        enableEdgeToEdge()

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CatsApp()
                }
            }
        }
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionGranted = checkSelfPermission(
                Manifest.permission.POST_NOTIFICATIONS
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED

            if (!permissionGranted) {
                notificationPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }
    }
}

private fun logFirebaseMessagingToken() {
    FirebaseMessaging.getInstance().token
        .addOnSuccessListener { token ->
            Log.d("FCM_TOKEN", token)
        }
        .addOnFailureListener { error ->
            Log.e("FCM_TOKEN", "Failed to get FCM token", error)
        }
}

@Composable
fun CatsApp() {
    val context = LocalContext.current
    val application = context.applicationContext as UprisingApplication

    val appInfoStorage = remember {
        AppInfoStorage(context.applicationContext)
    }

    var shouldShowAppInfo by remember {
        mutableStateOf(appInfoStorage.shouldShowAppInfo())
    }

    LaunchedEffect(shouldShowAppInfo) {
        if (shouldShowAppInfo) {
            application.analyticsReporter.logAppInfoShown()
        }
    }

    val provider = context.applicationContext as? AppComponentProvider
        ?: error("Application must implement AppComponentProvider")

    val appComponent = provider.getAppComponent()
    val navController = rememberNavController()

    val catsViewModelFactory = remember {
        CatsViewModelFactory(
            searchBreedsUseCase = appComponent.getSearchBreedsUseCase()
        )
    }

    val catsViewModel: CatsViewModel = viewModel(
        factory = catsViewModelFactory
    )

    NavHost(
        navController = navController,
        startDestination = "breed_list"
    ) {
        composable("breed_list") {
            LaunchedEffect(Unit) {
                application.crashReporter.logScreen("breed_list")
            }

            CatsScreen(
                viewModel = catsViewModel,
                onBreedClick = { breedId ->
                    application.crashReporter.logClick("open_breed_detail_$breedId")
                    navController.navigate("breed_detail/$breedId")
                },
                onCustomViewClick = {
                    application.crashReporter.logClick("open_custom_view")
                    navController.navigate("custom_view")
                }
            )
        }
        composable("custom_view") {
            CustomChartScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "breed_detail/{breedId}",
            arguments = listOf(
                navArgument("breedId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val breedId = backStackEntry.arguments?.getString("breedId")
                ?: return@composable

            LaunchedEffect(breedId) {
                application.crashReporter.logScreen("breed_detail")
            }

            val detailViewModelFactory = remember(breedId) {
                object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        if (modelClass.isAssignableFrom(BreedDetailViewModel::class.java)) {
                            val component = appComponent
                                .breedDetailComponentFactory()
                                .create(breedId)

                            @Suppress("UNCHECKED_CAST")
                            return component.viewModel() as T
                        }

                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                    }
                }
            }

            val detailViewModel: BreedDetailViewModel = viewModel(
                key = "breed_detail_$breedId",
                factory = detailViewModelFactory
            )

            BreedDetailScreen(
                viewModel = detailViewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
    if (shouldShowAppInfo) {
        AppInfoDialog(
            onDismissClick = {
                application.analyticsReporter.logAppInfoClosed()
                appInfoStorage.markAppInfoAccepted()
                shouldShowAppInfo = false
            }
        )
    }
}