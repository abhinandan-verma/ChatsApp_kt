package com.abhicoding.chatsapp.chat.groups

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.database.database


fun createNewGroup(groupName: String ,groupDesc: String, context: Context){

    val database = Firebase.database
    val reference = database.getReference("Groups")

    val group = Group(groupName,groupDesc)
    reference.child(groupName).setValue(group).addOnSuccessListener {
        Log.d("msg","Group $groupName created successfully")
        Toast.makeText(context,"Group $groupName created successfully",Toast.LENGTH_SHORT).show()
    }.addOnFailureListener {
        Toast.makeText(context,"Error: ${it.message.toString()}\nGroup could not be created",Toast.LENGTH_SHORT).show()
    }
}



