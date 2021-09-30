package com.example.productssamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var login: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        email = findViewById(R.id.et_email)
        password = findViewById(R.id.et_pass)
        login = findViewById(R.id.btn_login)

        login.setOnClickListener {
            if(email.text.toString().trim().isEmpty()){
                email.error= "Email is Empty"
                email.requestFocus()
            }

            else if (Patterns.EMAIL_ADDRESS.matcher(email.text).matches().not())
            {
                email.error = "Enter Correct Format"
                email.requestFocus()

            }
            else if(password.text.toString().trim().isEmpty()){
                password.error = "Password is Empty"
                password.requestFocus()
            }
            else if (password.text.toString().trim().length < 8)
            {
                password.error = "Password should be more than 8 digit"
                password.requestFocus()

            }
            else
            {
                val intent = Intent(applicationContext , DrugsListActivity::class.java)
                startActivity(intent)

            }
        }
    }
}