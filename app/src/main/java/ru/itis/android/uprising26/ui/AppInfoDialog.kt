package ru.itis.android.uprising26.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.itis.android.uprising26.R

@Composable
fun AppInfoDialog(
    onDismissClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
        },
        title = {
            Text(text = stringResource(R.string.app_info_title))
        },
        text = {
            Text(text = stringResource(R.string.app_info_text))
        },
        confirmButton = {
            Button(
                onClick = onDismissClick
            ) {
                Text(text = stringResource(R.string.app_info_button))
            }
        }
    )
}