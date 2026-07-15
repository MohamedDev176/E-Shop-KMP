package com.vf.multi.component.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun HeadLines(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        modifier = modifier,
        fontSize = 34.sp,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center,
        softWrap = true,
        letterSpacing = 5.sp,
        fontWeight = FontWeight.W700,
        lineHeight = 1.sp,
        maxLines = 1,
        minLines = 1
    )
}

@Preview
@Composable
fun HeadLinesPreview() {
    HeadLines(title = "Headlines")
}