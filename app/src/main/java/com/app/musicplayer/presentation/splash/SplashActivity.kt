package com.app.musicplayer.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.app.musicplayer.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { true }
        }
        //TODO(actions to be executed while splash is in display)
        super.onCreate(savedInstanceState)
            lifecycleScope.launch {
                delay(3000)

                val intent = Intent(
                    this@SplashActivity,
                    MainActivity::class.java)
                startActivity(intent)
                finish()
            }


    }



}