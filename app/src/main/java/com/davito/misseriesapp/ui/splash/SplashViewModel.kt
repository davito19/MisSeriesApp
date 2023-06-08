package com.davito.misseriesapp.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.davito.misseriesapp.data.UserRepository

class SplashViewModel : ViewModel() {

    private val userRepository = UserRepository()
    private val _isSessionActive: MutableLiveData<Boolean> = MutableLiveData()
    val isSessionActive: LiveData<Boolean> = _isSessionActive

    fun validateSessionActive() {
        _isSessionActive.postValue(userRepository.isSessionActive())

    }

}