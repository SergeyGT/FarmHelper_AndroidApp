package com.example.farmhelper

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class addPersonal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_personal)

        val fio = findViewById<EditText>(R.id.addFio)
        val proffesion = findViewById<EditText>(R.id.addProf)
        val ok: Button = findViewById(R.id.btnOk)

        ok.setOnClickListener {
            val addFio: String = fio.text.toString()
            val addProf: String = proffesion.text.toString()

            // Создание Intent для передачи данных обратно в coreApp
            val resultIntent = Intent().apply {
                putExtra("FIO", addFio)
                putExtra("PROF", addProf)
            }

            // Установка результата и завершение активности
            setResult(Activity.RESULT_OK, resultIntent)
            finish()

        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}