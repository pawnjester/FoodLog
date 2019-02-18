package com.andela.logfooddiary.ui.authentication.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import com.andela.logfooddiary.MainActivity
import com.andela.logfooddiary.R
import com.andela.logfooddiary.ui.authentication.Registration.RegistrationActivity
import com.andela.logfooddiary.utils.IS_LOGGED_IN
import com.andela.logfooddiary.utils.persistToSharedPrefs
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var isLoggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        button_login.setOnClickListener { _ -> loginUser() }
        register_link.setOnClickListener { _ -> startRegisterActivity() }
    }

    private fun loginUser() {
        val email = email_login_text.text.toString().trim()
        val password = password_login_text.text.toString().trim()

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
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(this,
                        "User successfully logged In",
                        Toast.LENGTH_LONG).show()
                isLoggedIn = true
                isLoggedIn.persistToSharedPrefs(this, IS_LOGGED_IN)

                Intent(this, MainActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            } else {
                Toast.makeText(this, "${it.exception}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun startRegisterActivity() {
        Intent(this, RegistrationActivity::class.java).apply {
            startActivity(this)
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
        }
    }

}
