package com.vf.multi.component.check

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
internal fun CheckMark() {
    Box(
        modifier = Modifier
            .size(26.dp)
            .background(Color(0xFFDDF3E4), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(16.dp)) {
            drawLine(
                color = Color(0xFF2DA85A),
                start = Offset(size.width * 0.1f, size.height * 0.55f),
                end = Offset(size.width * 0.4f, size.height * 0.85f),
                strokeWidth = 4f
            )
            drawLine(
                color = Color(0xFF2DA85A),
                start = Offset(size.width * 0.4f, size.height * 0.85f),
                end = Offset(size.width * 0.9f, size.height * 0.2f),
                strokeWidth = 4f
            )
        }
    }
}