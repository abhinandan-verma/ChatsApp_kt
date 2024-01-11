package com.abhicoding.chatsapp.phoneauth

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.abhicoding.chatsapp.getActivity
import com.abhicoding.chatsapp.signin.auth
import com.abhicoding.chatsapp.signin.storedVerificationId
import com.google.firebase.FirebaseException
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

private fun signInWithPhoneAuthCredential( context: Context, credential: PhoneAuthCredential) {
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