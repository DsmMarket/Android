package com.dsm.dsmmarketandroid.presentation.ui.password.passwordConfirm

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import com.dsm.dsmmarketandroid.R
import com.dsm.dsmmarketandroid.databinding.ActivityPasswordConfirmBinding
import com.dsm.dsmmarketandroid.presentation.ui.base.BaseActivity
import com.dsm.dsmmarketandroid.presentation.ui.password.changePassword.ChangePasswordActivity
import com.dsm.dsmmarketandroid.presentation.util.setEditorActionListener
import kotlinx.android.synthetic.main.activity_password_confirm.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class PasswordConfirmActivity : BaseActivity<ActivityPasswordConfirmBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_password_confirm

    private val viewModel: PasswordConfirmViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tb_password_confirm.setNavigationOnClickListener { finish() }

        et_original_password.setEditorActionListener(EditorInfo.IME_ACTION_DONE) { viewModel.confirmPassword() }

        viewModel.intentChangePassword.observe(this, Observer {
            startActivity<ChangePasswordActivity>("email" to it.first, "authCode" to it.second)
        })

        viewModel.finishActivityEvent.observe(this, Observer { finish() })

        viewModel.toastInvalidPasswordEvent.observe(this, Observer { toast(getString(R.string.fail_diff_password)) })

        viewModel.toastServerErrorEvent.observe(this, Observer { toast(getString(R.string.fail_server_error)) })

        binding.viewModel = viewModel
    }
}
