package com.dsm.dsmmarketandroid.presentation.ui.post.postRent

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.lifecycle.Observer
import com.dsm.dsmmarketandroid.R
import com.dsm.dsmmarketandroid.databinding.ActivityPostRentBinding
import com.dsm.dsmmarketandroid.presentation.base.BaseActivity
import com.dsm.dsmmarketandroid.presentation.ui.post.postRent.rentTime.SelectRentTimeDialog
import com.dsm.dsmmarketandroid.presentation.ui.postCategory.PostCategoryActivity
import com.dsm.dsmmarketandroid.presentation.util.Analytics
import com.dsm.dsmmarketandroid.presentation.util.LoadingDialog
import com.dsm.dsmmarketandroid.presentation.util.PermissionUtil
import com.dsm.mediapicker.MediaPicker
import com.dsm.mediapicker.enum.PickerOrientation
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_post_rent.*
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostRentActivity : BaseActivity<ActivityPostRentBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_post_rent

    companion object {
        private const val SELECT_IMAGE = 1
        private const val CATEGORY = 2
    }

    private val viewModel: PostRentViewModel by viewModel()

    override fun viewInit() {
        tb_post_rent.setNavigationOnClickListener { finish() }

        cl_category.setOnClickListener { startActivityForResult(Intent(this, PostCategoryActivity::class.java), CATEGORY) }

        iv_select_image.setOnClickListener {
            if (PermissionUtil.isReadExternalStorageAllow(this)) {
                PermissionUtil.requestReadExternalStorage(this)
                Snackbar.make(iv_select_image, getString(R.string.fail_permission_denied), Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            MediaPicker.createImage(this@PostRentActivity)
                .single()
                .theme(R.style.AppTheme)
                .toolbarBackgroundColor(R.color.colorPrimary)
                .toolbarTextColor(R.color.colorWhite)
                .orientation(PickerOrientation.PORTRAIT)
                .start(SELECT_IMAGE)
        }

        btn_select_time.setOnClickListener { SelectRentTimeDialog().show(supportFragmentManager, "") }
    }

    override fun observeViewModel() {
        val `this` = this@PostRentActivity
        viewModel.run {
            finishActivityEvent.observe(`this`, Observer { finish() })

            toastEvent.observe(`this`, Observer { toast(it) })

            showLoadingDialogEvent.observe(`this`, Observer { LoadingDialog.show(supportFragmentManager) })

            hideLoadingDialogEvent.observe(`this`, Observer { LoadingDialog.hide() })

            postRentLogEvent.observe(`this`, Observer { Analytics.logEvent(`this`, Analytics.POST_RENT, it) })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PermissionUtil.requestReadExternalStorage(this)
        binding.viewModel = viewModel
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_IMAGE) {
                val result = MediaPicker.getResult(data)
                if (result.isNotEmpty()) {
                    val imagePath = MediaPicker.getResult(data)[0]
                    iv_select_image.setImageBitmap(BitmapFactory.decodeFile(imagePath))
                    viewModel.setPhoto(imagePath)
                }
            } else if (requestCode == CATEGORY) {
                viewModel.setCategory(data?.getStringExtra("category") ?: "")
            }
        }
    }
}
