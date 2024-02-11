package com.abhicoding.chatsapp.message

import com.google.firebase.auth.FirebaseAuth

data class Message(val senderId: String = "",
                   val message: String = "",
                   val time: Long? = null,
                   val isMine: Boolean? = null)
    fun isMine(message: Message): Boolean{
        return message.senderId == FirebaseAuth.getInstance().currentUser?.email
    }



