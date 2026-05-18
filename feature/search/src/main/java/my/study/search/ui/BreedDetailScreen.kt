package my.study.search.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import my.study.domain.model.BreedModel
import my.study.search.R

@Composable
fun BreedDetailScreen(
    viewModel: BreedDetailViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    BackHandler {
        onBack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(16.dp)
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back)
            )
        }
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.errorType != null -> {
                ErrorCard(
                    errorType = uiState.errorType,
                    errorCode = uiState.errorCode,
                    onBack = onBack
                )
            }
            uiState.breed != null -> {
                BreedDetailContent(
                    breed = uiState.breed!!
                )
            }
        }
    }
}

@Composable
fun ErrorCard(
    errorType: ErrorType?,
    errorCode: Int?,
    onBack: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = when (errorType) {
                    ErrorType.NETWORK -> stringResource(R.string.error_network)
                    ErrorType.EMPTY -> stringResource(R.string.error_empty)
                    ErrorType.AUTH -> stringResource(R.string.error_auth)
                    ErrorType.API -> stringResource(R.string.error_server, errorCode ?: 0)
                    ErrorType.UNKNOWN -> stringResource(R.string.error_unknown)
                    null -> ""
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onBack) {
                Text(text = stringResource(R.string.back))
            }
        }
    }
}

@Composable
fun BreedDetailContent(
    breed: BreedModel
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = breed.name,
                style = MaterialTheme.typography.headlineSmall
            )
            if (!breed.origin.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.breed_origin, breed.origin!!)
                )
            }
            if (!breed.lifeSpan.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.breed_life_span, breed.lifeSpan!!),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (!breed.temperament.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.breed_temperament, breed.temperament!!),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (!breed.description.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.breed_description, breed.description!!),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}