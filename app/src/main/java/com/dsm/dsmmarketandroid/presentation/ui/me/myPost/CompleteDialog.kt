package com.dsm.dsmmarketandroid.presentation.ui.me.myPost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dsm.dsmmarketandroid.R
import com.dsm.dsmmarketandroid.presentation.util.ProductType
import com.dsm.dsmmarketandroid.presentation.util.isPurchase
import kotlinx.android.synthetic.main.dialog_complete_purchase.*

class CompleteDialog : DialogFragment() {

    private val viewModel: MyPostViewModel by lazy { ViewModelProviders.of(activity!!)[MyPostViewModel::class.java] }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.dialog_complete_purchase, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_complete.setOnClickListener {
            if ((arguments?.getInt("type") ?: 0).isPurchase()) {
                viewModel.completePost(arguments?.getInt("position") ?: -1, ProductType.PURCHASE)
            } else {
                viewModel.completePost(arguments?.getInt("position") ?: -1, ProductType.RENT)
            }
        }

        btn_cancel.setOnClickListener { dismiss() }

        viewModel.dismissEvent.observe(this, Observer { dismiss() })
    }
}