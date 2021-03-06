package com.dsm.dsmmarketandroid.presentation.ui.main.me.password.passwordConfirm

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import com.dsm.dsmmarketandroid.R
import com.dsm.dsmmarketandroid.databinding.ActivityPasswordConfirmBinding
import com.dsm.dsmmarketandroid.presentation.base.BaseActivity
import com.dsm.dsmmarketandroid.presentation.ui.main.me.password.changePassword.ChangePasswordActivity
import com.dsm.dsmmarketandroid.presentation.util.setEditorActionListener
import kotlinx.android.synthetic.main.activity_password_confirm.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConfirmPasswordActivity : BaseActivity<ActivityPasswordConfirmBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_password_confirm

    private val viewModel: ConfirmPasswordViewModel by viewModel()

    override fun viewInit() {
        tb_password_confirm.setNavigationOnClickListener { finish() }

        et_original_password.setEditorActionListener(EditorInfo.IME_ACTION_DONE) { if (btn_confirm_password.isClickable) viewModel.confirmPassword() }
    }

    override fun observeViewModel() {
        val `this` = this@ConfirmPasswordActivity
        viewModel.run {
            intentChangePasswordEvent.observe(`this`, Observer { startActivity<ChangePasswordActivity>() })

            finishActivityEvent.observe(`this`, Observer { finish() })

            toastEvent.observe(`this`, Observer { toast(it) })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
    }
}
