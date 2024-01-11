package com.abhicoding.chatsapp.chat.groups

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun Groups() {
    val context = LocalContext.current
    val viewModel = GroupViewModel(context)
    val openDialog = remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }



    SetData(viewModel, openDialog)


    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Group Name") },
            text = {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "Enter Group Name") }
                )
            },
            textContentColor = Color.Black,
            confirmButton = {
                Button(
                    onClick = {
                        createNewGroup(name, "Default Group Description", context)
                        openDialog.value = false

                    },
                    content = { Text(text = "Save") }
                )
            },
            dismissButton = {
                Button(
                    onClick = {
                        Toast.makeText(context, "Cancelling...", Toast.LENGTH_SHORT).show()
                        openDialog.value = false
                    },
                    content = { Text(text = "Cancel") }
                )
            }
        )
    }
}


