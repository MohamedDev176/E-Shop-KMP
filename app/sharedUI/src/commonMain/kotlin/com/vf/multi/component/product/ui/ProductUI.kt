package com.vf.multi.component.product.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vf.multi.component.product.model.Product


@Composable
internal fun ProductRow(left: Product, right: Product) {
    Row(modifier = Modifier.fillMaxWidth()) {
        ProductCard(
            product = left,
            modifier = Modifier
                .weight(1f)
                .padding(end = 6.dp),
        )
        ProductCard(
            product = right,
            modifier = Modifier
                .weight(1f)
                .padding(start = 6.dp),
        )
    }
}

@Composable
internal fun ProductCard(product: Product, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 1.dp,
        shadowElevation = 1.dp,
        color = MaterialTheme.colorScheme.surface,
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
                ) {
                    Text("Product")
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = product.title, style = MaterialTheme.typography.titleSmall, maxLines = 1)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${product.price}  |  ${product.rating}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}