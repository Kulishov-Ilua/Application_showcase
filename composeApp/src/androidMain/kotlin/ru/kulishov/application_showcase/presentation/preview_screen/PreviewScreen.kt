package ru.kulishov.application_showcase.presentation.preview_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import application_showcase.composeapp.generated.resources.Res
import application_showcase.composeapp.generated.resources.biglogo
import application_showcase.composeapp.generated.resources.hello
import application_showcase.composeapp.generated.resources.to_application
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun PreviewScreenUI(navigate: ()->Unit){
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Image(painterResource(Res.drawable.biglogo), "RuStore",
            modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.FillWidth)
    }
    Box(Modifier.padding(bottom = 50.dp).fillMaxSize(), contentAlignment = Alignment.BottomCenter){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(stringResource(Res.string.hello), style = MaterialTheme.typography.headlineLarge)
            Button(
                onClick = {
                    navigate()
                },
                shape = CircleShape,
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Text(stringResource(Res.string.to_application), style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.surface))
            }
        }
    }
}