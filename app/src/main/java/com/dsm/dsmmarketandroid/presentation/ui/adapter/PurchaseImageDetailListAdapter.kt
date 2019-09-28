package com.dsm.dsmmarketandroid.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dsm.dsmmarketandroid.R
import kotlinx.android.synthetic.main.item_purchase_image_detail.view.*

class PurchaseImageDetailListAdapter : RecyclerView.Adapter<PurchaseImageDetailListAdapter.ViewHolder>() {

    var listItems = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_purchase_image_detail, parent, false))

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            Glide.with(itemView)
                .load(listItems[adapterPosition])
                .error(R.drawable.image_error)
                .placeholder(R.drawable.image_placeholder)
                .into(itemView.iv_image)
        }
    }
}