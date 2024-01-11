package com.abhicoding.chatsapp.signup

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.abhicoding.chatsapp.ChatActivity
import com.abhicoding.chatsapp.User
import com.abhicoding.chatsapp.signin.auth
import com.abhicoding.chatsapp.ui.theme.ChatsAppTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.firestore

class SignUpActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatsAppTheme {
                SignUpDialog()


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
        Toast.makeText(this, "Authenticating ${currentUser.email}", Toast.LENGTH_LONG).show()
        Toast.makeText(this, "Redirecting...", Toast.LENGTH_LONG).show()
        val intent = Intent(this, ChatActivity::class.java)
        startActivity(intent)
        finish()
    }
}

fun signUpUser(name: String, email: String, password: String, context: Context) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(ContentValues.TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user, context)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            context,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        updateUI(null, context)
                    }
                }.addOnSuccessListener {
                    Log.d("msg","User signed in Successfully")
                    Toast.makeText(context ,"You Signed In Successfully",Toast.LENGTH_SHORT).show()

                   val user = User(name, email, password)
                    val db = Firebase.firestore
                   val userCollection = db.collection("Users")
                    userCollection.document(email).set(user).addOnSuccessListener(){
                        Toast.makeText(context,"User $name document created successfully",Toast.LENGTH_SHORT).show()
                        Log.d("msg","User $name document created successfully")
                    }
                }


        }

fun updateUI(user: FirebaseUser?, context: Context) {
        Log.d("tag","Updating... for ${user.toString()}")
        Toast.makeText(context,"Updating for ${user.toString()}",Toast.LENGTH_SHORT).show()
    }