package com.abhicoding.chatsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.abhicoding.chatsapp.chat.groups.Groups
import com.abhicoding.chatsapp.ui.theme.ChatsAppTheme
import kotlinx.coroutines.launch

class ChatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatsAppTheme {
                MyActivity()
            }
        }
    }
}

@Preview
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyActivity() {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val tabs = listOf("Groups","Chats","Calls")
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            contentColor = Color.Black,
            containerColor = Color.Cyan
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(title) }
                )
            }
        }

        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> Groups()
                1 -> Chats()
                2 -> Calls()
            }
        }
    }
}
@Composable
fun Chats() {
    // Content for Tab 2
}

@Composable
fun Calls() {
    // Content for Tab 3
}




