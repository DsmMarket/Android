package com.dsm.data.dataSource.account

import io.reactivex.Flowable
import retrofit2.Response

interface AccountDataSource {

    fun getUserNick(): Flowable<Response<Map<String, String>>>

    fun changeUserNick(newNick: String): Flowable<Response<Unit>>
}