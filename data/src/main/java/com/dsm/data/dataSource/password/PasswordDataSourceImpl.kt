package com.dsm.data.dataSource.password

import com.dsm.data.addSchedulers
import com.dsm.data.remote.Api
import io.reactivex.Flowable
import retrofit2.Response

class PasswordDataSourceImpl(private val api: Api) : PasswordDataSource {
    override fun confirmPassword(password: String): Flowable<Response<Map<String, String>>> =
        api.confirmPassword(password).addSchedulers()

    override fun changePassword(params: Any): Flowable<Response<Unit>> =
        api.changePassword(params).addSchedulers()

    override fun sendTempPassword(email: String): Flowable<Response<Unit>> =
        api.sendTempPassword(email).addSchedulers()
}