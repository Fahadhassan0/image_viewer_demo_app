package com.image.viewer.demo.api

import android.util.Log
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>?): T? {

        val response = call.invoke()
        return if (response!!.isSuccessful && response.body() != null) {
            Log.d(TAG, response.body().toString())
            response.body()
        } else {
            Log.d(TAG, response.code().toString())
            Log.d(TAG, response.message())
            null
        }
    }

    companion object {
        private const val TAG = "SafeApiRequest"
    }
}