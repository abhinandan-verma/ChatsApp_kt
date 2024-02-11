package com.abhicoding.chatsapp.signin

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhicoding.chatsapp.phoneauth.onLoginClicked
import com.abhicoding.chatsapp.phoneauth.verifyPhoneNumberWithCode
import com.abhicoding.chatsapp.signup.SignUpActivity

@Composable
fun CompleteDialogContent() {
    val context = LocalContext.current
    var name by remember {
        mutableStateOf("")
    }
    var phoneNumber by remember {
        mutableStateOf("")
    }
    var otp by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var isOtpVisible by remember {
        mutableStateOf(false)
    }
    var email by remember { mutableStateOf("") }
    var password by remember {
        mutableStateOf("")
    }
    Card(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(1f)
            .wrapContentHeight(),

        shape = RoundedCornerShape(15.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(1f)
                .wrapContentHeight(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login with Email and Password",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Enter Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Enter Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Button(
                onClick = {
                    Log.d("msg", "Sign in Initiated")
                    signInWithEmail(email, password, context)
                },
                colors = ButtonDefaults.textButtonColors(
                    Color.Cyan
                ),
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = "Login ",
                    color = Color.Blue
                )
            }

            Button(
                onClick = {
                    val intent = Intent(context, SignUpActivity::class.java)
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.textButtonColors(
                    Color.Magenta
                ),
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = "Create Account",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(50.dp))

    Card(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(top = 16.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(1f)
                .wrapContentHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Login With Phone Number",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
            TextField(
                value = phoneNumber,
                onValueChange = {
                    if (it.length <= 14) {
                        phoneNumber = it
                    }
                },
                label = { Text("Phone Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
            if (isOtpVisible) {
                TextField(
                    value = otp,
                    onValueChange = { otp = it },
                    placeholder = { Text(text = "Enter OTP") },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(top = 8.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
            }
            if (!isOtpVisible) {
                Button(
                    onClick = {
                        onLoginClicked(context, phoneNumber) {
                            Toast.makeText(context, "Sent Otp Visible", Toast.LENGTH_SHORT).show()
                            Log.d("Msg", "Sent OTP Visible")
                            isOtpVisible = true
                        }
                    }, colors = ButtonDefaults.textButtonColors(
                        Color.Green
                    ),
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(top = 8.dp)
                ) {
                    Text(text = "Sent OTP", color = Color.Blue)

                }
            } else {
                Button(
                    onClick = {
                        verifyPhoneNumberWithCode(
                            context,
                            storedVerificationId,
                            otp.text
                        )
                    },
                    colors = ButtonDefaults.textButtonColors(Color.Cyan),
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(8.dp)
                ) {
                    Text(text = "Verify", color = Color.White)
                }
            }
        }
    }
}
