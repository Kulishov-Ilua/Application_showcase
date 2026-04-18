package ru.kulishov.application_showcase.presentation.uikit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import application_showcase.composeapp.generated.resources.Res
import application_showcase.composeapp.generated.resources.error
import application_showcase.composeapp.generated.resources.refresh
import application_showcase.composeapp.generated.resources.thunderstorm
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ErrorScreen(
    isRefresh:(()->Unit)? = null
){
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Icon(painterResource(Res.drawable.thunderstorm),"error",
                tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier.size(40.dp))
            Text(stringResource(Res.string.error), style = MaterialTheme.typography.titleLarge)
            Button(
                {
                    if(isRefresh!=null){
                        isRefresh()
                    }

                }
            ) {
                Text(stringResource(Res.string.refresh), style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}