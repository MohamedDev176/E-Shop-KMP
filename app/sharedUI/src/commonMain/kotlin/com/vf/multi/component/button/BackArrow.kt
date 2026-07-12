package com.vf.multi.component.button

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun BackArrow() {
    Canvas(modifier = Modifier.size(26.dp)) {
        val stroke = 5f
        val color = Color(0xFF202227)
        drawLine(color, start = Offset(size.width * 0.75f, size.height * 0.1f), end = Offset(size.width * 0.25f, size.height * 0.5f), strokeWidth = stroke)
        drawLine(color, start = Offset(size.width * 0.75f, size.height * 0.9f), end = Offset(size.width * 0.25f, size.height * 0.5f), strokeWidth = stroke)
    }
}