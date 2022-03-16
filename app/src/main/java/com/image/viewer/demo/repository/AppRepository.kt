package com.image.viewer.demo.repository

import com.image.viewer.demo.api.ApiService
import com.image.viewer.demo.api.SafeApiRequest
import com.image.viewer.demo.data.ImageModel
import com.image.viewer.demo.utilities.PREF_IMAGE_HEIGHT
import com.image.viewer.demo.utilities.PREF_IMAGE_WIDTH
import com.image.viewer.demo.utilities.PreferenceHelper
import javax.inject.Inject


class AppRepository @Inject constructor(
    private val apiService: ApiService,
    private val sharedPreferences: PreferenceHelper
) : SafeApiRequest() {

    suspend fun getImageSize(): ImageModel? {
        val imageModel = apiRequest {
            apiService.getImageSize()
        }

        //if image model is not null we will save response in shared prefs
        if (imageModel != null) {
            sharedPreferences.set(PREF_IMAGE_HEIGHT, imageModel.translated_height)
            sharedPreferences.set(PREF_IMAGE_WIDTH, imageModel.translated_width)
        }
        return imageModel;
    }

}