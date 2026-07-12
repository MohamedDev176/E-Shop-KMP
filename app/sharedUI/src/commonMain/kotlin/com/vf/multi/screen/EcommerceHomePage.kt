package com.vf.multi.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vf.multi.component.button.BottomNav
import com.vf.multi.component.category.CategorySection
import com.vf.multi.component.HeaderSection
import com.vf.multi.component.card.PromoCard
import com.vf.multi.component.input.SearchBar
import com.vf.multi.component.SectionTitle
import com.vf.multi.component.product.model.Product
import com.vf.multi.component.product.ui.ProductRow


@Composable
internal fun EcommerceHomePage() {
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