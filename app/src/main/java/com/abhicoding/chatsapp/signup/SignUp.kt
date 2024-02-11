package com.abhicoding.chatsapp.signup

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun SignUpDialog() {
    val dialogState: MutableState<Boolean> = remember {
        mutableStateOf(true)
    }
    Dialog(
        onDismissRequest = { dialogState.value = false },
        content = {
            CompleteSignUpContent()
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    )
}

@Composable
fun CompleteSignUpContent() {
    val context = LocalContext.current
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var name by remember {
        mutableStateOf("")
    }
    Card(
        modifier = Modifier
            .height(400.dp)
            .fillMaxWidth(1f)
            .wrapContentHeight(),
        shape = RoundedCornerShape(20.dp),
        
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(1f)
                .wrapContentHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Sign Up Here",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            TextField(value = name,
                onValueChange = { name = it },
                label = { Text(text = "Username") }
            )
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Button(
                onClick = {
                    Log.d("msg", "Signing Up User")
                    signUpUser(name, email, password, context)
                },
                colors = ButtonDefaults.textButtonColors(
                    Color.Cyan
                ),
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = "Create Account",
                    color = Color.Black
                )
            }
        }
    }

}