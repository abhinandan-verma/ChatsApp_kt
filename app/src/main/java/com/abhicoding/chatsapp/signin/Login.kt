package com.abhicoding.chatsapp.signin

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.abhicoding.chatsapp.ChatActivity
import com.abhicoding.chatsapp.signup.updateUI

fun signInWithEmail(email: String, password: String, context: Context){

    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithEmail:success")
                val user = auth.currentUser
                updateUI(user,context)
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.exception)
                Toast.makeText(
                    context,
                    "Authentication failed.",
                    Toast.LENGTH_SHORT,
                ).show()
                updateUI(null,context)
            }
        }.addOnSuccessListener {
            Log.d("msg","Sign in successful")
            Toast.makeText(context,"Sign in successful\nRedirecting to Chats",Toast.LENGTH_SHORT).show()
            val intent = Intent(context,ChatActivity::class.java)
            context.startActivity(intent)
        }
}