package com.shang.localchatgpt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.shang.localchatgpt.databinding.ItemMessageBinding

class MessageAdapter : ListAdapter<MessageBean, MessageViewHolder>(MessageDiffUtil) {

    companion object{
        const val QUESTION = 0
        const val ANSWER = 1
    }

    private object MessageDiffUtil : DiffUtil.ItemCallback<MessageBean>() {
        override fun areItemsTheSame(oldItem: MessageBean, newItem: MessageBean): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MessageBean, newItem: MessageBean): Boolean {
            return oldItem.message == newItem.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is MessageBean.Question -> QUESTION
            is MessageBean.Answer -> ANSWER
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return when(viewType){
            QUESTION->{
                MessageViewHolder.Self(binding)
            }
            ANSWER->{
                MessageViewHolder.Gpt(binding)
            }
            else->throw IllegalArgumentException("MessageAdapter throw IllegalArgumentException ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}