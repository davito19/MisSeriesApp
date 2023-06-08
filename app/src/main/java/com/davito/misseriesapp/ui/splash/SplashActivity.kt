package com.davito.misseriesapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.davito.misseriesapp.databinding.ActivitySplashBinding
import com.davito.misseriesapp.ui.login.SignInActivity
import com.davito.misseriesapp.ui.main.MainActivity
import java.util.*
import kotlin.concurrent.timerTask

class SplashActivity : AppCompatActivity() {

    private lateinit var biding: ActivitySplashBinding
    private lateinit var splashViewModel: SplashViewModel
    private var isSessionActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biding = ActivitySplashBinding.inflate(layoutInflater)
        splashViewModel = ViewModelProvider(this)[SplashViewModel::class.java]
        val view = biding.root
        setContentView(view)

        splashViewModel.validateSessionActive()

        splashViewModel.isSessionActive.observe(this) { active ->
            this.isSessionActive = active
        }

        val timer = Timer()
        timer.schedule(
            timerTask {
                if (!isSessionActive) {
                    val intent = Intent(this@SplashActivity, SignInActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }, 2000
        )

        Log.d("onCreate", "Ok onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("onStart", "Ok onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("onResume", "Ok onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("onPause", "Ok onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("onStop", "Ok onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("onRestart", "Ok onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroy", "Ok onDestroy")
    }

}