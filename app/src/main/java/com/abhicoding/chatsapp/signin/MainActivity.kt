package com.abhicoding.chatsapp.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.abhicoding.chatsapp.ChatActivity
import com.abhicoding.chatsapp.ui.theme.ChatsAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            ChatsAppTheme {
                LoginDialog()

            }
        }
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload(currentUser)
        }
    }

    private fun reload(currentUser: FirebaseUser) {
        Toast.makeText(this, "Authenticating ${currentUser.email}\nRedirecting...", Toast.LENGTH_LONG).show()
        val intent = Intent(this, ChatActivity::class.java)
        startActivity(intent)
        finish()
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






