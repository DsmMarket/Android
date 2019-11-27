package com.dsm.dsmmarketandroid.presentation.ui.main.purchase.purchaseImage

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.PagerSnapHelper
import com.dsm.dsmmarketandroid.R
import com.dsm.dsmmarketandroid.custom.LinePagerIndicatorDecoration
import com.dsm.dsmmarketandroid.databinding.ActivityPurchaseImageBinding
import com.dsm.dsmmarketandroid.presentation.base.BaseActivity
import com.dsm.dsmmarketandroid.presentation.ui.adapter.PurchaseImageDetailListAdapter
import kotlinx.android.synthetic.main.activity_purchase_image.*
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class PurchaseImageActivity : BaseActivity<ActivityPurchaseImageBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_purchase_image

    private val viewModel: PurchaseImageViewModel by viewModel()

    private val postId: Int by lazy { intent.getIntExtra("post_id", -1) }

    private val imageAdapter = PurchaseImageDetailListAdapter()

    override fun viewInit() {
        tb_purchase_image.setNavigationOnClickListener { finish() }

        rv_purchase_image.run {
            adapter = imageAdapter
            rv_purchase_image.addItemDecoration(LinePagerIndicatorDecoration())
            PagerSnapHelper().attachToRecyclerView(this)
        }

        viewModel.getPurchaseImage(postId)
    }

    override fun observeViewModel() {
        viewModel.imageList.observe(this, Observer { imageAdapter.addItems(it) })

        viewModel.toastEvent.observe(this, Observer { toast(it) })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
    }
}
