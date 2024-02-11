package com.abhicoding.chatsapp.message

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhicoding.chatsapp.message.ui.theme.ChatsAppTheme
import com.abhicoding.chatsapp.sealed.DataState

class ChatActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatsAppTheme {
                val groupName = intent.getStringExtra("GROUP_NAME")
                val viewModel = groupName?.let { ChatViewModel(it) }
                Toast.makeText(this, "GroupName is $groupName", Toast.LENGTH_LONG).show()
                Log.d("msg", "GroupName is $groupName")
                Column {
                    TopAppBar(title = {
                        if (groupName != null) {
                            Text(
                                text = groupName,
                                color = Color.Green,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    },
                        actions = {
                            IconButton(onClick = {
                                viewModel?.signOut(this@ChatActivity)
                            }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "SignOut")
                            }
                        }
                    )
                    if (viewModel != null) {
                        Log.d("msg", "${viewModel.response} is not Null")
                        SetMessage(viewModel, groupName)
                    } else {
                        Log.d("msg", "Viewmodel is Null")
                    }
                }
            }
        }
    }

    @Composable
    fun SetMessage(viewModel: ChatViewModel, groupName: String) {
        when (val result = viewModel.response.value) {
            is DataState.ChatSuccess -> {
                ShowMessageList(result.data, groupName, viewModel)
                Log.d("msg", "Opening ChatsActivity ${result.data}")
            }

            is DataState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Loading...",
                        color = Color.Magenta)
                    CircularProgressIndicator(color = Color.Cyan)
                }
            }

            is DataState.Failure -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error!! Try Again\n")
                    Text(
                        text = result.message,
                        fontSize = 30.sp,
                    )
                }
            }

            else -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error!! Try Again\n")
                    Text(
                        text = "Error Fetching Data\nPerhaps Empty List 🫥",
                        fontSize = 30.sp,
                    )
                }
            }
        }
    }

    @Composable
    fun ShowMessageList(
        messages: MutableList<Message>,
        groupName: String,
        viewModel: ChatViewModel
    ) {
        Log.d("msg", "ShowMessageList() is Called")
        var text by remember {
            mutableStateOf("")
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight(.92f)
                    .border(4.dp, Color.Blue)
                    .padding(5.dp)
            ) {
                items(messages) { message ->
                  Column(
                        modifier = Modifier.fillMaxWidth(),
                      //  veArrangement = if (isMine(message)) Arrangement.End else Arrangement.Start
                      horizontalAlignment = if (isMine(message)) Alignment.End else Alignment.Start
                    ) {

                        if (isMine(message)){
                            Text(
                                text = message.message,
                                color = Color.White,
                                modifier = Modifier
                                    .background(Color.Blue, shape = CircleShape)
                                    .padding(10.dp)
                            )
                        }else{
                            Text(
                                text = message.message,
                                color = Color.Black,
                                modifier = Modifier
                                    .background(Color.LightGray, shape = CircleShape)
                                    .padding(10.dp)
                            )
                        }
//                      Text(text = message.time.toString(),
//                          modifier = Modifier.size(12.dp)
//                      )

                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(1f)
                    .padding(top = 8.dp)
            )
            {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = {
                        Text(
                            text = "Type Message Here",
                            color = Color.White
                        )
                    },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .background(Color.White)
                )
                OutlinedButton(onClick = { viewModel.sendMessage(text, groupName,true) },
                    shape = CircleShape,
                    colors = ButtonDefaults.outlinedButtonColors(Color.Blue),
                        ) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
                }
            }
        }
    }
}


