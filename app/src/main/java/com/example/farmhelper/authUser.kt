package com.example.farmhelper

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class authUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth_user)

        val loginUser: EditText = findViewById(R.id.Login_auth)
        val passUser: EditText = findViewById(R.id.Password_auth)
        val btnSign: Button = findViewById(R.id.buttonSign_auth)

        val registr: TextView = findViewById(R.id.registration)

        btnSign.setOnClickListener {
            val loginSign = loginUser.text.toString()
            val passSign = passUser.text.toString()

            val db = DBFarm(this, null)
            val result = db.getUser(loginSign, passSign)

            if (result) {
               Toast.makeText(this, "Вы успешно вошли в аккаунт", Toast.LENGTH_LONG).show()

                loginUser.text.clear()
                passUser.text.clear()

                val intentMainPage = Intent(this, mainPage::class.java)
                startActivity(intentMainPage)
            } else {
                Toast.makeText(this, "Данные аккаунта введены неверно или аккаунт не зарегистрирован",
                    Toast.LENGTH_LONG).show()
                loginUser.text.clear()
                passUser.text.clear()
                loginUser.setHintTextColor(Color.RED)
                passUser.setHintTextColor(Color.RED)

            }
        }

        registr.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}