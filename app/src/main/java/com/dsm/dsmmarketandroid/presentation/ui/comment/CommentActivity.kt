package com.dsm.dsmmarketandroid.presentation.ui.comment

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.dsm.dsmmarketandroid.R
import com.dsm.dsmmarketandroid.databinding.ActivityCommentBinding
import com.dsm.dsmmarketandroid.presentation.ui.adapter.CommentListAdapter
import com.dsm.dsmmarketandroid.presentation.ui.addComment.AddCommentActivity
import com.dsm.dsmmarketandroid.presentation.ui.base.BaseActivity
import com.dsm.dsmmarketandroid.presentation.ui.report.ReportCommentDialog
import kotlinx.android.synthetic.main.activity_comment.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommentActivity : BaseActivity<ActivityCommentBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_comment

    private val viewModel: CommentViewModel by viewModel()

    private val postId: Int by lazy { intent.getIntExtra("post_id", -1) }
    private val type: Int by lazy { intent.getIntExtra("type", -1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tb_comment.setNavigationOnClickListener { finish() }
        setSupportActionBar(tb_comment)

        val adapter = CommentListAdapter(viewModel)
        rv_comment.adapter = adapter

        viewModel.getCommentList(postId, type)

        viewModel.listItems.observe(this, Observer { adapter.setItems(it) })

        viewModel.toastServerErrorEvent.observe(this, Observer { toast(getString(R.string.fail_server_error)) })

        viewModel.dialogReportComment.observe(this, Observer {
            val args = Bundle()
            args.putInt("post_id", postId)
            args.putInt("type", type)
            args.putString("nick", it)
            val fragment = ReportCommentDialog()
            fragment.arguments = args
            fragment.show(supportFragmentManager, "")
        })

        binding.viewModel = viewModel
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_comment_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_comment -> startActivity<AddCommentActivity>("post_id" to postId, "type" to type)
        }
        return super.onOptionsItemSelected(item)
    }
}
