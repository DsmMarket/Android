package com.dsm.dsmmarketandroid.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dsm.dsmmarketandroid.R
import kotlinx.android.synthetic.main.item_detail_image.view.*

class DetailImagePagerAdapter : RecyclerView.Adapter<DetailImagePagerAdapter.ViewHolder>() {

    var imageList = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_detail_image, parent, false))

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            Glide.with(itemView)
                .load(imageList[adapterPosition])
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error)
                .into(itemView.iv_image)
        }
    }
}