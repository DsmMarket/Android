package com.dsm.dsmmarketandroid.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dsm.dsmmarketandroid.R
import com.dsm.dsmmarketandroid.presentation.model.ChatModel
import kotlinx.android.synthetic.main.item_date.view.*
import kotlinx.android.synthetic.main.item_my_chat.view.*

class ChatListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM_MY_CHAT = 0
        private const val ITEM_FOREIGN_CHAT = 1
        private const val ITEM_DATE = 2
        private const val ITEM_LOADING = 3
    }

    val listItems = arrayListOf<ChatModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_MY_CHAT -> MyChatViewHolder(inflater.inflate(R.layout.item_my_chat, parent, false))
            ITEM_FOREIGN_CHAT -> ForeignChatViewHolder(inflater.inflate(R.layout.item_foreign_chat, parent, false))
            ITEM_DATE -> DateHolder(inflater.inflate(R.layout.item_date, parent, false))
            else -> LoadingHolder(inflater.inflate(R.layout.item_loading, parent, false))
        }
    }

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when (holder) {
            is MyChatViewHolder -> holder.bind()
            is ForeignChatViewHolder -> holder.bind()
            is DateHolder -> holder.bind()
            else -> {
            }
        }

    override fun getItemViewType(position: Int): Int =
        when (listItems[position]) {
            is ChatModel.MyChat -> ITEM_MY_CHAT
            is ChatModel.ForeignChat -> ITEM_FOREIGN_CHAT
            is ChatModel.Date -> ITEM_DATE
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

    inner class DateHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            val item = listItems[adapterPosition] as ChatModel.Date
            itemView.tv_date.text = item.date
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
        listItems.removeAt(listItems.size - 1)  // remove loading
        if (listItems.size != 0) {
            listItems.removeAt(listItems.size - 1)  // remove date
        }
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

    fun addDate(now: String) {
        if (listItems.size == 0) {
            listItems.add(ChatModel.Date(now))
            return
        }
        when (val item = listItems.first()) {
            is ChatModel.MyChat -> {
                if (now != item.date) {
                    listItems.add(0, ChatModel.Date(now))
                }
            }
            is ChatModel.ForeignChat -> {
                if (now != item.date) {
                    listItems.add(0, ChatModel.Date(now))
                }
            }
        }
    }
}