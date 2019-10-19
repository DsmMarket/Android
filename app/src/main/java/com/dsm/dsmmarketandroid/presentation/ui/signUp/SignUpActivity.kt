package com.dsm.dsmmarketandroid.presentation.ui.signUp

import android.os.Bundle
import androidx.lifecycle.Observer
import com.dsm.dsmmarketandroid.R
import com.dsm.dsmmarketandroid.custom.LinePagerIndicatorDecoration
import com.dsm.dsmmarketandroid.databinding.ActivitySignUpBinding
import com.dsm.dsmmarketandroid.presentation.ui.adapter.SignUpPagerAdapter
import com.dsm.dsmmarketandroid.presentation.ui.base.BaseActivity
import com.dsm.dsmmarketandroid.presentation.util.LoadingDialog
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_sign_up

    private val viewModel: SignUpViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tb_sign_up.setNavigationOnClickListener { finish() }

        vp_sign_up.adapter = SignUpPagerAdapter(supportFragmentManager, lifecycle)
        vp_sign_up.addItemDecoration(LinePagerIndicatorDecoration())

        viewModel.toastEmailInvalidEvent.observe(this, Observer { toast(getString(R.string.fail_invalid_email)) })

        viewModel.toastPasswordDiffEvent.observe(this, Observer { toast(getString(R.string.fail_diff_password)) })

        viewModel.finishActivityEvent.observe(this, Observer { finish() })

        viewModel.toastExistentEmailEvent.observe(this, Observer { toast(getString(R.string.fail_existent_email)) })

        viewModel.toastExistentNameEvent.observe(this, Observer { toast(getString(R.string.fail_existent_nick)) })

        viewModel.toastServerErrorEvent.observe(this, Observer { toast(getString(R.string.fail_server_error)) })

        viewModel.showLoadingDialogEvent.observe(this, Observer { LoadingDialog.show(supportFragmentManager) })

        viewModel.hideLoadingDialogEvent.observe(this, Observer { LoadingDialog.hide() })

        binding.viewModel = viewModel
    }
}
