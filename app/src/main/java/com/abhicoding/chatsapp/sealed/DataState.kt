package com.abhicoding.chatsapp.sealed

import com.abhicoding.chatsapp.chat.groups.Group
import com.abhicoding.chatsapp.message.Message

sealed class DataState {
    class ChatSuccess(val data: MutableList<Message>): DataState()
    class Success(val data:MutableList<Group>): DataState()
    class Failure(val message: String): DataState()
    data object  Loading: DataState()
    data object Empty : DataState()
}