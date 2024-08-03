package com.example.farmhelper

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Locale

class zpCash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_zp_cash)

        fun convertDateFormat(date: String, fromFormat: String = "dd.MM.yyyy", toFormat: String = "yyyy-MM-dd"): String {
            val inputDateFormat = SimpleDateFormat(fromFormat, Locale.getDefault())
            val outputDateFormat = SimpleDateFormat(toFormat, Locale.getDefault())
            val parsedDate = inputDateFormat.parse(date)
            return outputDateFormat.format(parsedDate)
        }

        val employeeIdZp = intent.getIntExtra("EMPLOYEE_ID", -1)
        val startDate: EditText = findViewById(R.id.startDate)
        val endDate: EditText = findViewById(R.id.endDate)
        val zp: TextView = findViewById(R.id.zpSum)
        val btnCheck: Button = findViewById(R.id.btnCheckZp)

        btnCheck.setOnClickListener {
            val start = startDate.text.toString().trim()
            val end = endDate.text.toString().trim()
            val formatStartDate = convertDateFormat(start,"dd.MM.yyyy", "yyyy-MM-dd")
            val formatEndDate = convertDateFormat(end,"dd.MM.yyyy", "yyyy-MM-dd")


            val db = DBFarm(this, null)
            val totalSum = db.calculateSalary(employeeIdZp, formatStartDate, formatEndDate)
            zp.setText(totalSum.toString())
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}