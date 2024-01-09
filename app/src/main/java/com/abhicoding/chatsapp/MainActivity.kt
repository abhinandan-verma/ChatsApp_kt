package com.abhicoding.chatsapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.abhicoding.chatsapp.auth.onLoginClicked
import com.abhicoding.chatsapp.auth.verifyPhoneNumberWithCode
import com.abhicoding.chatsapp.ui.theme.ChatsAppTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatsAppTheme {

                LoginDialog()
            }
        }
    }
}

@Composable
fun LoginDialog() {

    val dialogState: MutableState<Boolean> = remember {
        mutableStateOf(true)
    }
    Dialog(
        onDismissRequest = { dialogState.value = false },
        content = {
            CompleteDialogContent()
        },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    )
}

val auth = FirebaseAuth.getInstance()
var storedVerificationId: String = ""

@Composable
fun CompleteDialogContent() {
    val context = LocalContext.current
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
            .height(600.dp)
            .fillMaxWidth(1f)
            .wrapContentHeight(),
        shape = RoundedCornerShape(10.dp)
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
                fontSize = 20.sp
            )
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Enter Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            TextField(value = password,
                onValueChange = { password = it },
                label = { Text(text = "Enter Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Button(onClick = {
                Log.d("msg","Button was Clicked")
            },
                modifier = Modifier.padding(8.dp),
            ) {
                Text(text = "Login ")
            }
        }
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
                fontSize = 20.sp
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
                        Color.Cyan
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




