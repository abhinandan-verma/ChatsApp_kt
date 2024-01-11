package com.abhicoding.chatsapp.chat.groups

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.abhicoding.chatsapp.sealed.DataState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class GroupViewModel(context: Context){

    val response: MutableState<DataState> = mutableStateOf(DataState.Empty)

    init {
        fetchAllGroups(context)
    }

    private fun fetchAllGroups(context: Context) {
        val groupList = mutableListOf<Group>()
        response.value = DataState.Loading
        FirebaseDatabase.getInstance().getReference("Groups")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (childSnapshot in snapshot.children) {
                        val group = childSnapshot.getValue(Group::class.java)
                        group?.let { groupList.add(it) }
                    }
                    response.value = DataState.Success(groupList)
                    Toast.makeText(context,"Retrieved Data Successfully", Toast.LENGTH_SHORT).show()
                    Log.d("msg","Retrieved Data Successfully")
                }

                override fun onCancelled(error: DatabaseError) {
                    response.value = DataState.Failure(error.message)
                    Log.e(ContentValues.TAG, "Failed to read value $error", error.toException())
                    Toast.makeText(context,"Failed to read data due to $error", Toast.LENGTH_LONG).show()
                }
            })
    }
}