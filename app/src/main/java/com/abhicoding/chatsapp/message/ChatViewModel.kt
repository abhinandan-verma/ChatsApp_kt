package com.abhicoding.chatsapp.message

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.abhicoding.chatsapp.sealed.DataState
import com.abhicoding.chatsapp.signin.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatViewModel(groupName: String) : ViewModel(){
    val response: MutableState<DataState> = mutableStateOf(DataState.Empty)

    init {
        fetchAllMessage(groupName = groupName)
    }

     private fun fetchAllMessage(groupName: String) {
        val messageList = mutableListOf<Message>()
        response.value = DataState.Loading
        FirebaseDatabase.getInstance().reference.child(groupName)
           /* .addChildEventListener(object : ChildEventListener{
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val message = snapshot.getValue(Message::class.java)
                    if (message != null) {
                        messageList.add(message)
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val message = snapshot.getValue(Message::class.java)
                    message?.let {
                        val index = messageList.indexOfFirst { it.senderId == message.senderId }
                            messageList[index] = message
                        }
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    val message = snapshot.getValue(Message::class.java)
                   messageList.remove(message!!)
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    Log.d("msg","onChildMoved() is called")
                }

                override fun onCancelled(error: DatabaseError) {
                   Log.d("msg","onCancelled() is called")
                }

            } )*/
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (childSnapshot in snapshot.children) {
                        val message = childSnapshot.getValue(Message::class.java)
                        message?.let { messageList.add(it) }
                    }
                    response.value = DataState.ChatSuccess(messageList)
                    //   Toast.makeText(context,"Retrieved Message Successfully", Toast.LENGTH_SHORT).show()
                    Log.d("msg", "Retrieved Message Successfully")
                }

                override fun onCancelled(error: DatabaseError) {
                    response.value = DataState.Failure(error.message)
                    Log.e(
                        ContentValues.TAG,
                        "Failed to read message $error",
                        error.toException()
                    )
                    //  Toast.makeText(context,"Failed to read data due to $error", Toast.LENGTH_LONG).show()
                }
            })

    }

    fun sendMessage(messageText: String, groupName: String, isMine: Boolean){
         val reference = FirebaseDatabase.getInstance().getReference(groupName)

        if (messageText.trim() != "" ){
            val msg = FirebaseAuth.getInstance().currentUser?.email?.let {
                Message(
                    it,messageText,
                    System.currentTimeMillis(),
                    isMine
                )
            }

            val randomKey : String? = reference.push().key

            if (randomKey != null) {
                reference.child(randomKey).setValue(msg)
                Log.d("msg","$messageText Sent Successfully")
            }
        }
    }

    fun signOut(context: Context){
        FirebaseAuth.getInstance().signOut()
        Log.d("msg","User signed Out")
        val navigate = Intent(context,MainActivity::class.java)
        context.startActivity(navigate)

    }
}
