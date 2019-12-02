package com.dsm.dsmmarketandroid.presentation.ui.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dsm.dsmmarketandroid.databinding.ItemProductBinding
import com.dsm.dsmmarketandroid.presentation.model.ProductModel
import com.dsm.dsmmarketandroid.presentation.ui.main.purchase.purchaseDetail.PurchaseDetailActivity
import com.dsm.dsmmarketandroid.presentation.ui.main.rent.rentDetail.RentDetailActivity
import com.dsm.dsmmarketandroid.presentation.util.ProductType

class RecentListAdapter(private val productType: Int) : RecyclerView.Adapter<RecentListAdapter.ViewHolder>() {

    private var listItems = listOf<ProductModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    fun setItems(items: List<ProductModel>) {
        listItems = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val item = listItems[adapterPosition]
            val context = binding.root.context
            binding.product = item
            binding.clParent.setOnClickListener {
                val intent =
                    if (productType == ProductType.PURCHASE) Intent(context, PurchaseDetailActivity::class.java)
                    else Intent(context, RentDetailActivity::class.java)
                intent.putExtra("post_id", item.postId)
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    context as Activity,
                    Pair.create(binding.ivProductThumb as View, "image"),
                    Pair.create(binding.tvProductTitle as View, "title")
                )
                context.startActivity(intent, options.toBundle())
            }
        }
    }
}