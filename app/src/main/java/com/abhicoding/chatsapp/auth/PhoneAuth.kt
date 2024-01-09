package com.abhicoding.chatsapp.auth

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhicoding.chatsapp.auth
import com.abhicoding.chatsapp.getActivity
import com.abhicoding.chatsapp.storedVerificationId
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


fun verifyPhoneNumberWithCode(context: Context, verificationId: String, otp: String) {
    val credential = PhoneAuthProvider.getCredential(verificationId, otp)

    signInWithPhoneAuthCredential(context, credential)
}

fun onLoginClicked(context: Context, phoneNumber: String, onCodeSent: () -> Unit) {
    auth.setLanguageCode("en")
    val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d("msg", "Otp verified Successfully")
            Toast.makeText(context, "Otp verified Successfully", Toast.LENGTH_SHORT).show()
            signInWithPhoneAuthCredential(context, credential)
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            Log.d("msg", "Otp verification Failed")
            Toast.makeText(context, "Otp Verification Failed", Toast.LENGTH_SHORT).show()
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {

            Log.d("msg", "code sent $verificationId")
            storedVerificationId = verificationId
            onCodeSent()
            super.onCodeSent(verificationId, token)
        }
    }
    val options = context.getActivity()?.let {
        PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(it) // Activity (for callback binding)
            .setCallbacks(callback) // OnVerificationStateChangedCallbacks
            .build()
    }
    Log.d("msg", options.toString())
    if (options != null) {
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}

private fun signInWithPhoneAuthCredential(context: Context, credential: PhoneAuthCredential) {
    context.getActivity()?.let {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in is success update UI with the signed-in user's information
                    val user = task.result?.user
                    Log.d("msg", "$user Logged In")
                    Toast.makeText(context,"$user logged in", Toast.LENGTH_SHORT).show()
                } else {
                    // Signed in failed , display the message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // the verification code was invalid
                        Log.d("msg", "Wrong Otp")
                        Toast.makeText(context, "Wrong otp", Toast.LENGTH_SHORT).show()
                    }
                    // Update ui
                }
            }
    }
}