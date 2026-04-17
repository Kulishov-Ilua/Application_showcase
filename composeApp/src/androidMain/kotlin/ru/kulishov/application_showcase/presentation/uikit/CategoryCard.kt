package ru.kulishov.application_showcase.presentation.uikit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.kulishov.application_showcase.SelectedFile
import ru.kulishov.application_showcase.domain.model.Category
import ru.kulishov.application_showcase.toImageBitmap

@Composable
fun CategoryCard(category: Category) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(50.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.padding(start = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Box(Modifier.size(30.dp), contentAlignment = Alignment.Center) {
                val bitmap = SelectedFile("", "", category.icon).toImageBitmap()
                if (bitmap != null) {
                    Image(
                        bitmap,
                        contentDescription = category.name,
                        modifier = Modifier.fillMaxSize()
                    )
                }

            }
            Text(category.name, style = MaterialTheme.typography.titleMedium)
        }
    }
}