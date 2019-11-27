package com.dsm.data.dataSource.account

import com.dsm.data.addSchedulers
import com.dsm.data.remote.Api
import io.reactivex.Flowable
import retrofit2.Response

class AccountDataSourceImpl(private val api: Api) : AccountDataSource {

    override fun refreshToken(refreshToken: String): Flowable<Response<Map<String, Any>>> =
        api.refreshToken(refreshToken).addSchedulers()

    override fun getUserNick(): Flowable<Response<Map<String, String>>> =
        api.getUserNick().addSchedulers()

    override fun changeUserNick(newNick: String): Flowable<Response<Unit>> =
        api.changeUserNick(newNick).addSchedulers()
}