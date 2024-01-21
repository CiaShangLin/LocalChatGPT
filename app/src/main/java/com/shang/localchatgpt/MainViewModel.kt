package com.shang.localchatgpt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okio.ByteString

class MainViewModel : ViewModel() {

    private val mMessageFlow = MutableSharedFlow<List<MessageBean>?>()
    val messageFlow: SharedFlow<List<MessageBean>?> = mMessageFlow

    val chatGPTWebsocketListener = object : ChatGPTWebsocketListener() {
        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            mMessageList.add(MessageBean.Answer(text))
            viewModelScope.launch {
                mMessageFlow.emit(mMessageList.map { it })
            }
        }
    }

    private val mMessageList = mutableListOf<MessageBean>()

    fun sendQuestion(question:String){
        mMessageList.add(MessageBean.Question(question))
        viewModelScope.launch {
            mMessageFlow.emit(mMessageList.map { it })
        }
    }

}