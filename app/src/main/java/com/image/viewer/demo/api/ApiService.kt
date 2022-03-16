package com.image.viewer.demo.api

import com.image.viewer.demo.data.ImageModel
import com.image.viewer.demo.utilities.PARAM_TEST_ID
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("b/{id}")
    suspend fun getImageSize(
        @Path("id") id: String? = PARAM_TEST_ID
    ): Response<ImageModel>
}