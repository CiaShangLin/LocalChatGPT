package com.shang.localchatgpt

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.shang.localchatgpt.databinding.ItemMessageBinding

sealed class MessageViewHolder(protected val mBinding: ItemMessageBinding) :
    ViewHolder(mBinding.root) {

    fun bind(item: MessageBean) {
        mBinding.tvMessage.text=item.message
    }

    class Self(binding: ItemMessageBinding) : MessageViewHolder(binding) {
        init {
            mBinding.ivHead.setImageResource(R.drawable.icon_user)
        }
    }

    class Gpt(binding: ItemMessageBinding) : MessageViewHolder(binding) {
        init {
            mBinding.ivHead.setImageResource(R.drawable.icon_gpt4all)
        }
    }
}