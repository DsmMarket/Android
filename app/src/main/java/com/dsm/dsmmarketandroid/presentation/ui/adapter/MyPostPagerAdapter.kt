package com.dsm.dsmmarketandroid.presentation.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dsm.dsmmarketandroid.presentation.ui.main.me.myPost.purchase.MyPurchaseFragment
import com.dsm.dsmmarketandroid.presentation.ui.main.me.myPost.rent.MyRentFragment

class MyPostPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> MyPurchaseFragment()
            else -> MyRentFragment()
        }
}