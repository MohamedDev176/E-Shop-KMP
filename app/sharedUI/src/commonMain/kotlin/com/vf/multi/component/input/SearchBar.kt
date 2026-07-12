package com.vf.multi.component.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
internal fun SearchBar() {
    OutlinedTextField(
        value = "Search clothes, shoes, bags...",
        onValueChange = {},
        enabled = false,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
    )
}
