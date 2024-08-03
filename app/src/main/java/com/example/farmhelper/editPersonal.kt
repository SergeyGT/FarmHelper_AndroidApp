package com.example.farmhelper

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class editPersonal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_personal)

        val employeeId = intent.getIntExtra("EMPLOYEE_ID", -1)
        val dateOfWork: EditText = findViewById(R.id.dateWork)
        val typeOfWork: EditText = findViewById(R.id.typeWork)
        val hoursOfWork: EditText = findViewById(R.id.hoursWork)
        val priceOfWork: EditText = findViewById(R.id.priceHour)
        val hectareOfWork: EditText = findViewById(R.id.countGec)
        val hectarePriceOfWork: EditText = findViewById(R.id.priceGek)
        val btnSave: Button = findViewById(R.id.btnEdit)

        val db = DBFarm(this, null)


        if (employeeId == -1) {
            println("id not exists")
        }

        btnSave.setOnClickListener {
            val date = dateOfWork.text.toString().trim()
            val typeWork = typeOfWork.text.toString().trim()
            val hoursWork = hoursOfWork.text.toString().trim().toDoubleOrNull() ?: 0.0
            val priceHour = priceOfWork.text.toString().trim().toIntOrNull() ?: 0
            val hectareCount = hectareOfWork.text.toString().trim().toDoubleOrNull() ?: 0.0
            val hectarePrice = hectarePriceOfWork.text.toString().trim().toIntOrNull() ?: 0


            if (date == "" || typeWork == "" || hoursWork == 0.0 || priceHour == 0 || hectareCount == 0.0 || hectarePrice == 0) {
                Toast.makeText(this, "Заполните все ячейки поля", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            else {
                val salary = Salary(
                    employeeId = employeeId,
                    workDate = date,
                    workType = typeWork,
                    hours = hoursWork,
                    hoursRate = priceHour,
                    hectares = hectareCount,
                    hectaresRate = hectarePrice
                )

                val dbFarm = DBFarm(this, null)
                dbFarm.addSalary(salary)

                finish()
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}