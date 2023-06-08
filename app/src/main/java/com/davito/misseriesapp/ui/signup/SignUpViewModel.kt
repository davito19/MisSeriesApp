package com.davito.misseriesapp.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davito.misseriesapp.data.ResourceRemote
import com.davito.misseriesapp.data.UserRepository
import com.davito.misseriesapp.model.User
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    private val userRepository = UserRepository()

    private val _errorMsg: MutableLiveData<String?> = MutableLiveData()
    val errorMsg: LiveData<String?> = _errorMsg

    private val _isSuccessSignUp: MutableLiveData<Boolean> = MutableLiveData()
    val isSuccessSignUp: LiveData<Boolean> = _isSuccessSignUp

    fun validateFields(
        email: String,
        password: String,
        repeatPassword: String,
        name: String,
        genre: String,
        genreFavoritos: String
    ) {

        if (email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty() || name.isEmpty()) {
            _errorMsg.value = "debe digitar todos los campos"
        } else {
            if (password.length < 6) {
                _errorMsg.value = "la contraseña debe tener minimo 6 digitos"
            } else {
                if (password != repeatPassword) {
                    _errorMsg.value = "las contraseñas deben ser iguales"
                } else {
                    viewModelScope.launch {
                        val result = userRepository.signUpUser(email, password)
                        result.let { resourceRemote ->
                            when (resourceRemote) {
                                is ResourceRemote.Success -> {
                                    val user = User(
                                        uid = resourceRemote.data,
                                        name = name,
                                        email = email,
                                        genre = genre,
                                        genreFavoritos = genreFavoritos
                                    )
                                    //Todo almacenar usuario
                                    createUser(user)
                                    //_isSuccessSignUp.postValue(true)
                                }
                                is ResourceRemote.Error -> {
                                    var msg = resourceRemote.message
                                    when (resourceRemote.message) {
                                        "A network error (such as timeout, interrupted connection or unreachable host) has occurred." -> msg =
                                            "Revise su conexión a Internet"
                                        "The email address is already in use by another account." -> msg =
                                            "el correo electronico ya existe"
                                        "The email address is badly formatted." -> msg =
                                            "el correo electronico no es valido"
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

    }

    private fun createUser(user: User) {
        viewModelScope.launch {
            val result = userRepository.createUser(user)
            result.let { resourceRemote ->
                when(resourceRemote){
                    is ResourceRemote.Success -> {
                        _isSuccessSignUp.postValue(true)
                        _errorMsg.postValue("Registro exitoso")
                    }
                    is ResourceRemote.Error -> {
                        val msg = result.message
                        _errorMsg.postValue(msg)
                    }
                    else -> {

                    }
                }
            }
        }
    }
}