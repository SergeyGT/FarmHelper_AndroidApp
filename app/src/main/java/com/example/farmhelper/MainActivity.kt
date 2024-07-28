package com.example.farmhelper
import User
import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val loginUser: EditText = findViewById(R.id.Login)
        val passUser: EditText = findViewById(R.id.Password)
        val btnSign: Button = findViewById(R.id.buttonSign)

        val clrMain = loginUser.currentHintTextColor

        btnSign.setOnClickListener {
            val login = loginUser.text.toString().trim()
            val pass = passUser.text.toString().trim()

            if (login == "" || pass == "") {
                Toast.makeText(this, "Не введен пароль или логин", Toast.LENGTH_LONG).show()


                loginUser.text.clear()
                passUser.text.clear()
                loginUser.setHintTextColor(Color.RED)
                passUser.setHintTextColor(Color.RED)
            }
            else {
                loginUser.setHintTextColor(clrMain)
                passUser.setHintTextColor(clrMain)

                val user = User(login, pass)

                val db = DBFarm(this, null)
                db.addUser(user)

                Toast.makeText(this, "Пользователь $login зарегистрирован", Toast.LENGTH_LONG).show()

                loginUser.text.clear()
                passUser.text.clear()

            }
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}