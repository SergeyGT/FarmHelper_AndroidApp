package com.example.farmhelper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.Person
import androidx.recyclerview.widget.RecyclerView

class personAdapter(var persons: List<Personal>, var context: Context): RecyclerView.Adapter<personAdapter.MyViewHolder>() {
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val FIO: TextView = view.findViewById(R.id.FIO)
        val work: TextView = view.findViewById(R.id.work)
        val hours: TextView = view.findViewById(R.id.hours)
        val img: ImageView = view.findViewById(R.id.photoPersona)
        val prof: TextView = view.findViewById(R.id.Proff)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.personal_info, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return persons.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.FIO.text = persons[position].FIO
        holder.work.text = persons[position].work
        holder.hours.text = String.format("%.2f", persons[position].hours)
        holder.prof.text = persons[position].prof
    }
}