package com.davito.misseriesapp.ui.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.davito.misseriesapp.databinding.ActivitySignUpBinding
import com.davito.misseriesapp.ui.main.MainActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var signUpViewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        signUpViewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        val view = binding.root
        setContentView(view)

        signUpViewModel.errorMsg.observe(this) { errorMsg ->
            Toast.makeText(applicationContext, errorMsg, Toast.LENGTH_LONG)
                .show()
        }

        signUpViewModel.isSuccessSignUp.observe(this) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.dateButton.setOnClickListener {
            val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
            datePicker.show(supportFragmentManager, "fecha de nacimiento")
        }

        binding.button.setOnClickListener{
            prueba()
        }
    }

    private fun prueba() {
        val name = binding.nameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val repeatPassword = binding.passwordRepeatEditText.text.toString()
        val genre = if (binding.radioButton.isChecked) "Femenino" else "Masculino"
        val info = "Name: $name\nEmail: $email\n"

        var genreFavoritos = ""
        if(binding.checkBox.isChecked) genreFavoritos+="accion;"
        if(binding.checkBox2.isChecked) genreFavoritos+="aventura;"
        if(binding.checkBox3.isChecked) genreFavoritos+="suspenso;"
        if(binding.checkBox4.isChecked) genreFavoritos+="infantil;"
        if(binding.checkBox5.isChecked) genreFavoritos+="psicodelia;"
        if(binding.checkBox6.isChecked) genreFavoritos+="romance;"


        signUpViewModel.validateFields(email,password, repeatPassword, name, genre,genreFavoritos)
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        binding.dateButton.setText("$day/$month/$year")
    }
}