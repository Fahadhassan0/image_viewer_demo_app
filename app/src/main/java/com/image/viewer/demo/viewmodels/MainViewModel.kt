package com.image.viewer.demo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.image.viewer.demo.data.EdittextModel
import com.image.viewer.demo.data.ImageModel
import com.image.viewer.demo.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    //live data for image size response
    private val imageSizeLiveData: MutableLiveData<ImageModel> by lazy {
        MutableLiveData<ImageModel>()
    }

    private val isTextAddedLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private val textLiveData: MutableLiveData<EdittextModel> by lazy {
        MutableLiveData<EdittextModel>()
    }

    fun isTextAdded() = isTextAddedLiveData

    fun setTextAdded(added: Boolean) {
        isTextAddedLiveData.value = added
    }


    //calling get image size api
    fun getImageSize(): LiveData<ImageModel> {
        viewModelScope.launch(Dispatchers.IO) {
            imageSizeLiveData.postValue(repository.getImageSize())
        }
        return imageSizeLiveData
    }

    fun addText(model: EdittextModel) {
        textLiveData.value = model
    }

    fun getText() = textLiveData
}