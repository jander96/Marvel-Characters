package com.devj.dcine.data

import okhttp3.Interceptor
import okhttp3.Response
import java.math.BigInteger
import java.security.MessageDigest

class ApiKeyInterceptor(private val publicApiKey: String, private val privateApiKey: String) : Interceptor {
    override fun intercept(chain:Interceptor.Chain): Response {
        val timestamp = System.currentTimeMillis().toString()
        val hashInput = "$timestamp${privateApiKey}${publicApiKey}"
        val hash = md5(hashInput)

        val originalRequest = chain.request()
        val url = originalRequest.url.newBuilder()
            .addQueryParameter("ts", timestamp)
            .addQueryParameter("hash", hash)
            .addQueryParameter("apikey", publicApiKey)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(url)
            .build()

        return chain.proceed(newRequest)
    }
    fun md5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}