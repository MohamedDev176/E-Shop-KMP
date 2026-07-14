package com.vf.multi.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vf.multi.component.button.BackArrow
import com.vf.multi.component.button.ForwardArrow
import com.vf.multi.component.button.PrimaryButton
import com.vf.multi.component.button.SocialButton
import com.vf.multi.component.check.CheckMark
import com.vf.multi.component.input.InputCard

@Composable
internal fun LoginScreen() {
    var email by remember { mutableStateOf("muffin.sweet@gmail.com") }
    var password by remember { mutableStateOf("") }

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
            text = "Login",
            fontSize = 50.sp,
            lineHeight = 52.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1D1F24)
        )

        Spacer(modifier = Modifier.height(44.dp))

        InputCard(
            label = "Email",
            value = email,
            onValueChange = { email = it },
            trailing = { CheckMark() }
        )

        Spacer(modifier = Modifier.height(14.dp))

        InputCard(
            label = "Password",
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(26.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Forgot your password?",
                fontSize = 18.sp,
                color = Color(0xFF2D2E33)
            )
            Spacer(modifier = Modifier.width(10.dp))
            ForwardArrow()
        }

        Spacer(modifier = Modifier.height(28.dp))

        PrimaryButton(
            text = "Login",
            onClick = { /* Handle login click */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        Spacer(modifier = Modifier.height(150.dp))

        Text(
            text = "Or login with social account",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color(0xFF2B2C31),
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(22.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            SocialButton(
                modifier = Modifier.weight(1f),
                symbol = "G",
                symbolColor = Color(0xFF4285F4)
            )
            SocialButton(
                modifier = Modifier.weight(1f),
                symbol = "f",
                symbolColor = Color(0xFF4267B2)
            )
        }
    }
}