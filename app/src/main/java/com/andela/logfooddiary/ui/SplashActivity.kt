package com.andela.logfooddiary.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.andela.logfooddiary.MainActivity
import com.andela.logfooddiary.R
import com.andela.logfooddiary.ui.authentication.login.LoginActivity
import com.andela.logfooddiary.utils.CONSTANTS
import com.andela.logfooddiary.utils.IS_LOGGED_IN
import com.andela.logfooddiary.utils.getBooleanPrefValue

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val isLoggedIn = IS_LOGGED_IN.getBooleanPrefValue(this)
            if (isLoggedIn!!) goToMainActivity() else goToLoginActivity()
        }, CONSTANTS.TWO_SECONDS)

    }

    fun goToMainActivity() {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
        }
    }
    fun goToLoginActivity() {
        Intent(this, LoginActivity::class.java).apply {
            startActivity(this)
        }
    }
}
