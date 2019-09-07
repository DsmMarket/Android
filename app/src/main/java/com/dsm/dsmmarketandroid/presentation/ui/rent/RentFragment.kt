package com.dsm.dsmmarketandroid.presentation.ui.rent

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import com.dsm.dsmmarketandroid.R
import com.dsm.dsmmarketandroid.databinding.FragmentRentBinding
import com.dsm.dsmmarketandroid.presentation.ui.adapter.RentListAdapter
import com.dsm.dsmmarketandroid.presentation.ui.base.BaseFragment
import com.dsm.dsmmarketandroid.presentation.ui.category.CategoryActivity
import com.dsm.dsmmarketandroid.presentation.ui.interest.InterestActivity
import com.dsm.dsmmarketandroid.presentation.ui.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_rent.*
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class RentFragment : BaseFragment<FragmentRentBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_rent

    private val viewModel: RentViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        activity?.setTitle(R.string.rent)

        val adapter = RentListAdapter()
        rv_rent.adapter = adapter

        viewModel.rentLiveData.observe(this, Observer { adapter.submitList(it) })

        viewModel.networkState.observe(this, Observer { adapter.setNetworkState(it) })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> activity?.startActivity<SearchActivity>()
            R.id.category -> activity?.startActivity<CategoryActivity>()
            R.id.interest -> activity?.startActivity<InterestActivity>()
        }
        return super.onOptionsItemSelected(item)
    }
}