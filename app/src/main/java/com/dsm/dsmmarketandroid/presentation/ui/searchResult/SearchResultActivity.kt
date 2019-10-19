package com.dsm.dsmmarketandroid.presentation.ui.searchResult

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.dsm.dsmmarketandroid.R
import com.dsm.dsmmarketandroid.databinding.ActivitySearchResultBinding
import com.dsm.dsmmarketandroid.presentation.ui.adapter.SearchPagerAdapter
import com.dsm.dsmmarketandroid.presentation.ui.base.BaseActivity
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_search_result.*
import org.jetbrains.anko.startActivity

class SearchResultActivity : BaseActivity<ActivitySearchResultBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_search_result

    private val search: String by lazy { intent.getStringExtra("search") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ib_back.setOnClickListener { finish() }
        et_search.hint = search

        et_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                startNewSearchResult()
                true
            } else false
        }

        ib_search.setOnClickListener { startNewSearchResult() }

        tl_search.addTab(tl_search.newTab().setText(getString(R.string.purchase)))
        tl_search.addTab(tl_search.newTab().setText(getString(R.string.rent)))
        vp_search.adapter = SearchPagerAdapter(supportFragmentManager)
        vp_search.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tl_search))
        tl_search.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                vp_search.currentItem = tab!!.position
            }
        })
    }

    private fun startNewSearchResult() {
        val search = et_search.text.toString().trim()
        if (search.isNotBlank()) {
            startActivity<SearchResultActivity>("search" to search)
            finish()
        }
    }
}
