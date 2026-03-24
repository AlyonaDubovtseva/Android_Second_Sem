package my.study.search.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import my.study.domain.model.BreedModel

@Composable
fun BreedDetailScreen(
    breed: BreedModel,
    onBack: () -> Unit
) {
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
                contentDescription = stringResource(my.study.search.R.string.back))
        }
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
                if (breed.origin?.isNotEmpty() ?: false ) {
                    Text(
                        text = stringResource(my.study.search.R.string.breed_origin, breed.origin!!)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                if (breed.lifeSpan?.isNotEmpty() ?: false ) {
                    Text(
                        text = stringResource(my.study.search.R.string.breed_life_span,
                            breed.lifeSpan!!
                        ),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                if (breed.temperament?.isNotEmpty() ?: false ) {
                    Text(
                        text = stringResource(my.study.search.R.string.breed_temperament,
                            breed.temperament!!
                        ),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (breed.description?.isNotEmpty() ?: false ) {
                    Text(
                        text = stringResource(my.study.search.R.string.breed_description,
                            breed.description!!
                        ),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}