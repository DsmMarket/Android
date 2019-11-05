package com.dsm.dsmmarketandroid.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dsm.dsmmarketandroid.R
import com.dsm.dsmmarketandroid.presentation.model.ChatModel
import kotlinx.android.synthetic.main.item_my_chat.view.*

class ChatListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM_MY_CHAT = 0
        private const val ITEM_FOREIGN_CHAT = 1
        private const val ITEM_LOADING = 2
    }

    val listItems = arrayListOf<ChatModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_MY_CHAT -> MyChatViewHolder(inflater.inflate(R.layout.item_my_chat, parent, false))
            ITEM_FOREIGN_CHAT -> ForeignChatViewHolder(inflater.inflate(R.layout.item_foreign_chat, parent, false))
            else -> LoadingHolder(inflater.inflate(R.layout.item_loading, parent, false))
        }
    }

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when (holder) {
            is MyChatViewHolder -> holder.bind()
            is ForeignChatViewHolder -> holder.bind()
            else -> {
            }
        }

    override fun getItemViewType(position: Int): Int =
        when (listItems[position]) {
            is ChatModel.MyChat -> ITEM_MY_CHAT
            is ChatModel.ForeignChat -> ITEM_FOREIGN_CHAT
            is ChatModel.Loading -> ITEM_LOADING
        }

    inner class MyChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            val item = listItems[adapterPosition] as ChatModel.MyChat
            itemView.tv_time.text = item.time
            itemView.tv_message.text = item.content
        }
    }

    inner class ForeignChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            val item = listItems[adapterPosition] as ChatModel.ForeignChat
            itemView.tv_time.text = item.time
            itemView.tv_message.text = item.content
        }
    }

    inner class LoadingHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun addMyChatItem(item: ChatModel.MyChat) {
        listItems.add(0, item)
        notifyItemInserted(0)
    }

    fun addForeignChatItem(item: ChatModel.ForeignChat) {
        listItems.add(0, item)
        notifyItemInserted(0)
    }

    fun addItems(items: List<ChatModel>) {
        listItems.addAll(items)
        notifyDataSetChanged()
    }

    fun showLoading() {
        listItems.add(ChatModel.Loading)
        notifyDataSetChanged()
    }

    fun hideLoading() {
        listItems.remove(ChatModel.Loading)
        notifyDataSetChanged()
    }
}