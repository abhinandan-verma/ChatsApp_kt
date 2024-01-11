package com.abhicoding.chatsapp.sealed

import com.abhicoding.chatsapp.chat.groups.Group

sealed class DataState {
    class Success(val data:MutableList<Group>): DataState()
    class Failure(val message: String): DataState()
    data object  Loading: DataState()
    data object Empty : DataState()
}