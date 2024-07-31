package com.example.farmhelper

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar

class mainPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_page)

        val prog: ProgressBar = findViewById(R.id.progressBar)

        val handler = Handler(Looper.getMainLooper())
        var progressStatus = 0

        val runnable = object : Runnable {
            override fun run() {
                progressStatus += 20
                prog.progress = progressStatus
                if (progressStatus < 100) {
                    handler.postDelayed(this, 1500)
                }
            }
        }
        handler.post(runnable)

        // Задержка в миллисекундах
        val delayMillis: Long = 6000

       // Переход через указанное время
        Handler(Looper.getMainLooper()).postDelayed({
            val intentToCore = Intent(this, coreApp::class.java)
            startActivity(intentToCore)
            // Завершение текущей Activity, если нужно
            finish()
        }, delayMillis)

//        val intentToCore = Intent(this, coreApp::class.java)
//        startActivity(intentToCore)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}