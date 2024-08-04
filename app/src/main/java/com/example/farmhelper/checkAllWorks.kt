package com.example.farmhelper

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class checkAllWorks : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_check_all_works)

        val adapterWork: workAdapter
        val db = DBFarm(this, null)
        val allWorks: RecyclerView = findViewById(R.id.workStaff)
        val idEmployee = intent.getIntExtra("EMPLOYEE_ID", -1)
        val fio: TextView = findViewById(R.id.fioPers)

        fio.setText(intent.getStringExtra("FIO"))

        val dbInitial = db.getWorksForEmployee(idEmployee)
        adapterWork = workAdapter(dbInitial.toMutableList(), this)
        allWorks.layoutManager = LinearLayoutManager(this)
        allWorks.adapter = adapterWork


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}