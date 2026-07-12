package com.vf.multi

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.vf.multi.theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme {
        EcommerceHomePage()
    }
}

@Composable
private fun EcommerceHomePage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeContentPadding()
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                HeaderSection()
                Spacer(modifier = Modifier.height(16.dp))
                SearchBar()
                Spacer(modifier = Modifier.height(16.dp))
                PromoCard()
                Spacer(modifier = Modifier.height(20.dp))
                CategorySection()
                Spacer(modifier = Modifier.height(20.dp))
                SectionTitle(title = "Featured")
                Spacer(modifier = Modifier.height(12.dp))
                ProductRow(
                    left = Product("Oversized T-shirt", "$34.99", "4.8"),
                    right = Product("Loose Fit Hoodie", "$58.50", "4.7")
                )
                Spacer(modifier = Modifier.height(12.dp))
                ProductRow(
                    left = Product("Classic Sneakers", "$79.00", "4.9"),
                    right = Product("Crossbody Bag", "$42.99", "4.6")
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        BottomNav()
    }
}

@Composable
private fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Hello, Taqie",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = "Find your style",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
        }

        Surface(
            modifier = Modifier.size(42.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
            ) {
                Text(text = "U", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
private fun SearchBar() {
    OutlinedTextField(
        value = "Search clothes, shoes, bags...",
        onValueChange = {},
        enabled = false,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun PromoCard() {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.primary,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Summer Sale",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Up to 50% off selected items",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.clickable { }
                ) {
                    Text(
                        text = "Shop now",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))
            BoxImagePlaceholder()
        }
    }
}

@Composable
private fun BoxImagePlaceholder() {
    Surface(
        modifier = Modifier
            .size(84.dp),
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        ) {
            Text(text = "IMG", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@Composable
private fun CategorySection() {
    SectionTitle(title = "Categories")
    Spacer(modifier = Modifier.height(10.dp))

    val categories = listOf("All", "Women", "Men", "Shoes", "Bags", "Accessories")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
    ) {
        categories.forEachIndexed { index, category ->
            val selected = index == 0
            Surface(
                shape = RoundedCornerShape(999.dp),
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .border(
                        width = if (selected) 0.dp else 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(999.dp),
                    ),
            ) {
                Text(
                    text = category,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "See all",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

private data class Product(
    val title: String,
    val price: String,
    val rating: String,
)

@Composable
private fun ProductRow(left: Product, right: Product) {
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
private fun ProductCard(product: Product, modifier: Modifier = Modifier) {
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

@Composable
private fun BottomNav() {
    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val items = listOf("Home", "Discover", "Cart", "Profile")
        items.forEachIndexed { index, item ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
            ) {
                Text(
                    text = item,
                    color = if (index == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = if (index == 0) FontWeight.SemiBold else FontWeight.Normal,
                )
            }
        }
    }
}
