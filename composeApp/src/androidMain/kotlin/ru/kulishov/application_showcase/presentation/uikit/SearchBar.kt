package ru.kulishov.application_showcase.presentation.uikit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import application_showcase.composeapp.generated.resources.Res
import application_showcase.composeapp.generated.resources.find_app
import application_showcase.composeapp.generated.resources.outline_close_24
import application_showcase.composeapp.generated.resources.search
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SearchBox(
    inp: String,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        value = inp,
        onValueChange = { onChange(it) },
        textStyle = MaterialTheme.typography.bodyMedium,
        trailingIcon = {
            Icon(
                painterResource(if(inp=="") Res.drawable.search else Res.drawable.outline_close_24),
                contentDescription = "search",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.clickable{
                    if(inp!=""){
                        onChange("")
                    }
                }
            )
        },
        placeholder = { Text(stringResource(Res.string.find_app), style = MaterialTheme.typography.bodyMedium) },
        shape = CircleShape,
        colors = TextFieldColors(
            focusedTextColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.surfaceContainer,
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            errorTextColor = MaterialTheme.colorScheme.error,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            errorContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            cursorColor = MaterialTheme.colorScheme.primary,
            errorCursorColor = MaterialTheme.colorScheme.error,
            textSelectionColors = TextSelectionColors(
                handleColor = MaterialTheme.colorScheme.onSurface,
                backgroundColor = MaterialTheme.colorScheme.onSurface
            ),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurface,
            errorLeadingIconColor = MaterialTheme.colorScheme.onSurface,
            focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            errorTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            focusedLabelColor = MaterialTheme.colorScheme.onSurface,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
            disabledLabelColor = MaterialTheme.colorScheme.onSurface,
            errorLabelColor = MaterialTheme.colorScheme.onSurface,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurface,
            errorPlaceholderColor = MaterialTheme.colorScheme.onSurface,
            focusedSupportingTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedSupportingTextColor = MaterialTheme.colorScheme.onSurface,
            disabledSupportingTextColor = MaterialTheme.colorScheme.onSurface,
            errorSupportingTextColor = MaterialTheme.colorScheme.onSurface,
            focusedPrefixColor = MaterialTheme.colorScheme.onSurface,
            unfocusedPrefixColor = MaterialTheme.colorScheme.onSurface,
            disabledPrefixColor = MaterialTheme.colorScheme.onSurface,
            errorPrefixColor = MaterialTheme.colorScheme.onSurface,
            focusedSuffixColor = MaterialTheme.colorScheme.onSurface,
            unfocusedSuffixColor = MaterialTheme.colorScheme.onSurface,
            disabledSuffixColor = MaterialTheme.colorScheme.onSurface,
            errorSuffixColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier.fillMaxWidth().height(56.dp)

    )
}