package com.example.productssamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import java.util.regex.Matcher
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var login: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        email = findViewById(R.id.et_email)
        password = findViewById(R.id.et_pass)
        login = findViewById(R.id.btn_login)

        login.setOnClickListener {
            if(email.text.toString().trim().length ==0){
                email.setError("email is empty");
                email.requestFocus();
            }

            else if (!isEmailValid(email.text.toString().trim()))
            {
                email.setError("email not match as pattern email");
                email.requestFocus();

            }
            else if(password.text.toString().trim().length==0){
                password.setError("Password is empty");
                password.requestFocus();
            }
            else if (password.text.toString().trim().length < 8)
            {
                password.setError("Password should be more than 8 digit");
                password.requestFocus();

            }
            else
            {
                val intent = Intent(applicationContext , HomeActivity::class.java)
                startActivity(intent)

            }


        }
    }
    fun  isEmailValid( email : String) : Boolean {
        var expression : String = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        var pattern : Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        var   matcher : Matcher = pattern.matcher(email);
        return matcher.matches()
    }

}