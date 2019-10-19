package com.dsm.dsmmarketandroid.presentation.ui.interest

import android.os.Bundle
import androidx.lifecycle.Observer
import com.dsm.dsmmarketandroid.R
import com.dsm.dsmmarketandroid.databinding.ActivityInterestBinding
import com.dsm.dsmmarketandroid.presentation.ui.adapter.InterestPagerAdapter
import com.dsm.dsmmarketandroid.presentation.ui.base.BaseActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_interest.*
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class InterestActivity : BaseActivity<ActivityInterestBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_interest

    private val viewModel: InterestViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tb_interest.setNavigationOnClickListener { finish() }

        var isRentLoaded = false

        vp_interest.adapter = InterestPagerAdapter(supportFragmentManager, lifecycle)
        TabLayoutMediator(tl_interest, vp_interest, true) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.purchase)
                1 -> tab.text = getString(R.string.rent)
            }
        }.attach()
        // TODO 확장함수로 빼기
        tl_interest.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                vp_interest.currentItem = tab!!.position
                if (tab.position == 1) {
                    if (!isRentLoaded) {
                        viewModel.getInterestRent()
                        isRentLoaded = true
                    }
                }
            }

        })

        viewModel.toastServerErrorEvent.observe(this, Observer { toast(getString(R.string.fail_server_error)) })

        binding.viewModel = viewModel
    }
}
