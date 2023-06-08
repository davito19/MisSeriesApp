package com.davito.misseriesapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.davito.misseriesapp.databinding.ActivityLoginBinding
import com.davito.misseriesapp.ui.main.MainActivity
import com.davito.misseriesapp.ui.signup.SignUpActivity

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var singInViewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        singInViewModel = ViewModelProvider(this)[SignInViewModel::class.java]
        val view = binding.root
        setContentView(view)

        singInViewModel.errorMsg.observe(this) { errorMsg ->
            Toast.makeText(applicationContext, errorMsg, Toast.LENGTH_LONG)
                .show()
        }

        singInViewModel.isSuccessSignIn.observe(this) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.registerTextView.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.button.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            singInViewModel.validateFields(email, password)
        }
    }
}