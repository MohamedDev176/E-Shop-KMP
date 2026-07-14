package com.vf.multi.component.button

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.vf.multi.theme.Primary

@Composable
internal fun ForwardArrow() {
    Canvas(modifier = Modifier.size(18.dp)) {
        val stroke = 4f
        val color = Primary
        drawLine(color, start = Offset(size.width * 0.1f, size.height * 0.2f), end = Offset(size.width * 0.9f, size.height * 0.2f), strokeWidth = stroke)
        drawLine(color, start = Offset(size.width * 0.58f, size.height * 0.02f), end = Offset(size.width * 0.9f, size.height * 0.2f), strokeWidth = stroke)
        drawLine(color, start = Offset(size.width * 0.58f, size.height * 0.38f), end = Offset(size.width * 0.9f, size.height * 0.2f), strokeWidth = stroke)
    }
}
