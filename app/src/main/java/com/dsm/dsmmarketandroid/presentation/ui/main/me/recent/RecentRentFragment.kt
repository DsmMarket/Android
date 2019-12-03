package com.dsm.dsmmarketandroid.presentation.ui.main.me.recent

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dsm.dsmmarketandroid.R
import com.dsm.dsmmarketandroid.databinding.FragmentRecentRentBinding
import com.dsm.dsmmarketandroid.presentation.base.BaseFragment
import com.dsm.dsmmarketandroid.presentation.ui.adapter.RecentListAdapter
import com.dsm.dsmmarketandroid.presentation.util.ProductType
import kotlinx.android.synthetic.main.fragment_recent_rent.*

class RecentRentFragment : BaseFragment<FragmentRecentRentBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_recent_rent

    private val viewModel: RecentViewModel by lazy { ViewModelProviders.of(activity!!).get(RecentViewModel::class.java) }

    private val adapter = RecentListAdapter(ProductType.RENT)

    override fun viewInit() {
        rv_recent_rent.adapter = adapter
    }

    override fun observeViewModel() {
        viewModel.rentList.observe(this, Observer { adapter.setItems(it) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

}