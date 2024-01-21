package com.shang.localchatgpt

sealed class MessageBean(val message:String) {


    class Question(message: String) : MessageBean(message) {

    }

    class Answer(message: String) : MessageBean(message) {

    }
}