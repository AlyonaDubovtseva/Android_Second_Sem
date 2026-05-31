package my.study.search.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import my.study.domain.model.BreedModel
import my.study.search.R

@Composable
fun BreedListScreen(
    state: CatsUiState,
    onSearchQueryChange: (String) -> Unit,
    onBreedClick: (BreedModel) -> Unit,
    onCustomViewClick: () -> Unit,
    onRetry: () -> Unit,
    onClearError: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val sourceMessage = when (state.sourceInfoResId) {
        R.string.source_server -> stringResource(R.string.source_server)
        R.string.source_cache -> if (state.sourceInfoArg != null) {
            stringResource(R.string.source_cache, state.sourceInfoArg)
        } else {
            stringResource(R.string.source_cache)
        }
        else -> null
    }
    LaunchedEffect(sourceMessage) {
        sourceMessage?.let {
            snackbarHostState.showSnackbar(it)
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.cat_breeds),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onCustomViewClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.open_custom_view))
        }

        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.query,
            onValueChange = onSearchQueryChange,
            label = { Text(stringResource(R.string.search_hint)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                if (state.query.isNotEmpty()) {
                    IconButton(onClick = { onSearchQueryChange("") }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = stringResource(R.string.clear)
                        )
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            state.errorType != null -> {
                val errorMessage = when (state.errorType) {
                    ErrorType.NETWORK -> stringResource(R.string.error_network)
                    ErrorType.EMPTY -> stringResource(R.string.error_empty)
                    ErrorType.AUTH -> stringResource(R.string.error_auth)
                    ErrorType.API -> stringResource(R.string.error_server, state.errorCode ?: 0)
                    ErrorType.UNKNOWN -> stringResource(R.string.error_unknown)
                }
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = errorMessage,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row {
                            Button(onClick = onRetry) {
                                Text(text = stringResource(R.string.retry))
                            }
                            Spacer(modifier = Modifier.width(16.dp))

                        }
                    }
                }
            }
            state.breeds.isEmpty() && state.query.isNotBlank() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.error_empty),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.breeds, key = { it.id }) { breed ->
                        BreedCard(
                            breed = breed,
                            onClick = { onBreedClick(breed) }
                        )
                    }
                }
            }
        }
        }
    }
}

@Composable
fun BreedCard(
    breed: BreedModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = breed.name,
                style = MaterialTheme.typography.titleMedium
            )
            if (!breed.origin.isNullOrEmpty()) {
                Text(
                    text = breed.origin!!,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}