package com.abhicoding.chatsapp.chat.groups

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhicoding.chatsapp.sealed.DataState

class Group(var image: String? = null, val name: String = "", val description: String = "")

@Composable
fun SetData(viewModel: GroupViewModel, openDialog: MutableState<Boolean>) {
    when (val result = viewModel.response.value) {
        is DataState.Success -> {
            ShowLazyList(result.data,openDialog)
        }

        is DataState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
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
fun ShowLazyList(groups: MutableList<Group>, openDialog: MutableState<Boolean>) {
    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    openDialog.value = true
                },
                containerColor = Color.Green,
                contentColor = Color.Black
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        Modifier.padding(it)


        LazyColumn {
            items(groups) {
                ElevatedCard(
                    modifier = Modifier
                        .padding(8.dp),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Row(
                        Modifier
                            .background(
                                Brush.linearGradient(
                                    listOf(Color.Yellow, Color.Green, Color.Cyan)
                                )
                            )
                            .padding(16.dp)
                    ) {
                        Image(
                            imageVector = Icons.TwoTone.Home,
                            colorFilter = ColorFilter.tint(Color.Black),
                            alignment = Alignment.TopStart,
                            contentDescription = "Group",
                            modifier = Modifier.size(40.dp)

                        )
                        Spacer(modifier = Modifier.padding(3.dp))
                        Column(Modifier.weight(1f)) {
                            Text(
                                text = it.name,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                fontSize = 22.sp
                            )
                            Text(
                                text = it.description,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}
