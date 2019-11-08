package com.dsm.data.remote.token

import android.util.Log
import com.dsm.data.local.pref.PrefHelper
import com.dsm.domain.usecase.RefreshTokenUseCase
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject
import org.koin.core.KoinComponent
import org.koin.core.inject

class TokenInterceptor(private val prefHelper: PrefHelper) : Interceptor, KoinComponent {

    private val refreshTokenUseCase: RefreshTokenUseCase by inject()

    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("DEBUGLOG", "access token: " + prefHelper.getAccessToken().toString())

        val request = chain.request().newBuilder().run {
            addHeader("authorization", prefHelper.getAccessToken()!!)
            build()
        }

        val response = chain.proceed(request)
        val responseCopy = response.peekBody(Long.MAX_VALUE)
        val json = JSONObject(responseCopy.string())
        var refresh = true
        if (json.has("refresh")) {
            refresh = json.getBoolean("refresh")
        }

        if (response.code() == 401 && !refresh) {
            Log.d("DEBUGLOG", "access token expired")
            val refreshResponse = refreshTokenUseCase.create(prefHelper.getRefreshToken()!!).blockingFirst()

            return if (refreshResponse.code() == 200) {
                val accessToken = refreshResponse.body()!!["access_token"] as String
                Log.d("DEBUGLOG", "refresh token success: $accessToken")
                prefHelper.setAccessToken(accessToken)
                val newRequest = request.newBuilder().run {
                    removeHeader("authorization")
                    addHeader("authorization", accessToken)
                    build()
                }

                chain.proceed(newRequest)
            } else {
                response
            }
        }
        return response
    }
}