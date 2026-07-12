package com.vf.multi.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vf.multi.component.button.BackArrow
import com.vf.multi.component.button.PrimaryButton
import com.vf.multi.component.check.ErrorCross
import com.vf.multi.component.input.InputCard

@Composable
internal fun ForgotPasswordScreen() {
    var email by remember { mutableStateOf("email2") }

    val isEmailValid = remember(email) {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        emailRegex.matches(email)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 18.dp)
    ) {
        BackArrow()
        Spacer(modifier = Modifier.height(42.dp))

        Text(
            text = "Forgot password",
            fontSize = 50.sp,
            lineHeight = 52.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1D1F24)
        )

        Spacer(modifier = Modifier.height(68.dp))

        Text(
            text = "Please, enter your email address. You will receive\na link to create a new password via email.",
            fontSize = 18.sp,
            lineHeight = 26.sp,
            color = Color(0xFF2B2C31)
        )

        Spacer(modifier = Modifier.height(24.dp))

        InputCard(
            label = "Email",
            value = email,
            onValueChange = { email = it },
            trailing = { if (!isEmailValid) ErrorCross() },
            isError = !isEmailValid,
            labelColor = if (!isEmailValid) Color(0xFFEB2B1F) else Color(0xFF9B9B9B),
            errorText = if (!isEmailValid) "Not a valid email address. Should be your@email.com" else null
        )

        Spacer(modifier = Modifier.height(120.dp))

        PrimaryButton(
            text = "SEND",
            onClick = { /* Handle send action */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
    }
}

