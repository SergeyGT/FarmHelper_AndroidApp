package com.example.farmhelper

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
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

        val persons = arrayListOf<Personal>()
        persons.add(Personal(1, "Баранцов Петр Иванович", "Тракторист", 3.5,
            "Орошение", "base"))
        persons.add(Personal(2, "Парфенов Василий Сергеевич", "Тракторист", 9.5,
            "Посев", "base"))

        staff.layoutManager = LinearLayoutManager(this)
        staff.adapter = personAdapter(persons, this)

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