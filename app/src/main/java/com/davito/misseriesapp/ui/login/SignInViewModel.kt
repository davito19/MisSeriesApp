package com.davito.misseriesapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davito.misseriesapp.data.ResourceRemote
import com.davito.misseriesapp.data.UserRepository
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    private val userRepository = UserRepository()

    private val _errorMsg: MutableLiveData<String> = MutableLiveData()
    val errorMsg: LiveData<String> = _errorMsg

    private val _isSuccessSignIn: MutableLiveData<Boolean> = MutableLiveData()
    val isSuccessSignIn: LiveData<Boolean> = _isSuccessSignIn

    fun validateFields(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _errorMsg.value = "debe digitar todos los campos"
        } else {
            viewModelScope.launch {
                val result = userRepository.singInUser(email, password)
                result.let { resourceRemote ->
                    when (resourceRemote) {
                        is ResourceRemote.Success -> {
                            _isSuccessSignIn.postValue(true)
                        }
                        is ResourceRemote.Error -> {
                            var msg = resourceRemote.message
                            when (resourceRemote.message) {
                                "A network error (such as timeout, interrupted connection or unreachable host) has occurred." -> msg =
                                    "Revise su conexión a Internet"
                                "The email address is badly formatted." -> msg =
                                    "el correo electronico no es valido"
                                "The password is invalid or the user does not have a password." -> msg =
                                    "el usuario o contraseña estan invalidas"

                            }
                            _errorMsg.postValue(msg)
                        }
                        else -> {

                        }
                    }
                }
            }
        }
    }


}