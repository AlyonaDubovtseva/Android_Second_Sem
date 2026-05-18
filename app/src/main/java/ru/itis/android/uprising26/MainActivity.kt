package ru.itis.android.uprising26

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import my.study.di.ServiceLocator
import my.study.search.di.CatsViewModelFactory
import my.study.search.ui.CatsScreen
import my.study.search.ui.CatsViewModel



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val factory = CatsViewModelFactory(
                        searchBreedsUseCase = ServiceLocator.searchBreedsUseCase,
                        getBreedByIdUseCase = ServiceLocator.getBreedByIdUseCase
                    )
                    val viewModel: CatsViewModel = viewModel(factory = factory)
                    CatsScreen(viewModel = viewModel)
                }
            }
        }
    }
}
