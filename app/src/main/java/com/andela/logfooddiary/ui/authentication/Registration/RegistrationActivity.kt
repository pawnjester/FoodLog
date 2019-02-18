package com.andela.logfooddiary.ui.authentication.Registration

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import com.andela.logfooddiary.MainActivity
import com.andela.logfooddiary.R
import com.andela.logfooddiary.ui.authentication.login.LoginActivity
import com.andela.logfooddiary.utils.IS_LOGGED_IN
import com.andela.logfooddiary.utils.persistToSharedPrefs
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registration.*


class RegistrationActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var isLoggedIn = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        auth = FirebaseAuth.getInstance()

        button_registration.setOnClickListener { _ -> registerUser() }
        have_an_account.setOnClickListener { _ -> startLoginActivity() }
    }

    private fun registerUser() {
        val email = email_text.text.toString().trim()
        val password = password_text.text.toString().trim()

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,
                    "Please enter an email address",
                    Toast.LENGTH_LONG).show()
        }
        if (TextUtils.isEmpty(password) ||password.length < 6 ){
            Toast.makeText(this,
                    "Password cannot be less than six characters",
                    Toast.LENGTH_LONG).show()
        }
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_LONG).show()

                isLoggedIn = true
                isLoggedIn.persistToSharedPrefs(this, IS_LOGGED_IN)

                Intent(this, MainActivity::class.java).apply {
                    startActivity(this)
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
                    finish()
                }
            } else {
                Toast.makeText(this, "${it.exception}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun startLoginActivity() {
        Intent(this, LoginActivity::class.java).apply {
            startActivity(this)
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
        }
    }
}
