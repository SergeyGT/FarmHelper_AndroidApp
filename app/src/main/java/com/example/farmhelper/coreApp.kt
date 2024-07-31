package com.example.farmhelper

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class coreApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_core_app)

        val back: ConstraintLayout = findViewById(R.id.main)
        val switchTheme: Switch = findViewById(R.id.switchTheme1)
        // val btnCash: Button = findViewById(R.id.btnStuffCash)
        val staff: RecyclerView = findViewById(R.id.staffList)
        val labelStaff: TextView = findViewById(R.id.labelStuff)
        val btnAdd: Button = findViewById(R.id.addPerson)

        val persons: MutableList<Personal> = arrayListOf()
        persons.clear()
        persons.add(Personal(1, "Баранцов Петр Иванович", "Тракторист", 3.5,
            "Орошение", "base"))
        persons.add(Personal(2, "Парфенов Василий Сергеевич", "Тракторист", 9.5,
            "Посев", "base"))

        val adapter = personAdapter(persons, this)
        staff.layoutManager = LinearLayoutManager(this)
        staff.adapter = adapter

        // Обработка результата из addPersonal
        val addPersonResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val newFio = data?.getStringExtra("FIO") ?: return@registerForActivityResult
                val newProf = data?.getStringExtra("PROF") ?: return@registerForActivityResult

                val newId = persons.size + 1 // Новый ID для нового сотрудника
                val newPerson = Personal(newId, newFio, newProf, 0.0, "Новая работа", "base")

                // Добавление нового сотрудника в список и уведомление адаптера об изменениях
                persons.add(newPerson)
                adapter.notifyItemInserted(persons.size - 1)
            }
        }

        btnAdd.setOnClickListener {
            val intent = Intent(this, addPersonal::class.java)
            addPersonResultLauncher.launch(intent)
        }





        switchTheme.setOnCheckedChangeListener { compoundButton, checked ->
            if (!checked) {
                back.setBackgroundColor(Color.parseColor("#064a8f"))
                labelStaff.setTextColor(Color.parseColor("#ffffff"))

            } else {
                back.setBackgroundColor(Color.parseColor("#d0f280"))
                labelStaff.setTextColor(Color.parseColor("#000000"))


            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}