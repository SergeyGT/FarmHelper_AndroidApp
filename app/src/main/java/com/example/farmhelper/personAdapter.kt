package com.example.farmhelper

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.Person
import androidx.recyclerview.widget.RecyclerView

class personAdapter(var persons:  MutableList<employee>, var context: Context): RecyclerView.Adapter<personAdapter.MyViewHolder>() {
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val FIO: TextView = view.findViewById(R.id.FIO)
        val prof: TextView = view.findViewById(R.id.Proff)
        val btnEdit: Button = view.findViewById(R.id.btnEditPerson)
        val btnZp: Button = view.findViewById(R.id.btnStuffCash)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.personal_info, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return persons.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.FIO.text = persons[position].fio
//        holder.work.text = persons[position].work
//        holder.hours.text = String.format("%.2f", persons[position].hours)
        holder.prof.text = persons[position].proffesion

        holder.btnEdit.setOnClickListener {
            val intentEdit = Intent(context, editPersonal::class.java)
            context.startActivity(intentEdit)
        }
        holder.btnZp.setOnClickListener {
            val intentZp = Intent(context, zpCash::class.java)
            context.startActivity(intentZp)
        }
    }

    fun updateData(newPersons: MutableList<employee>) {
        persons.clear()
        persons.addAll(newPersons)
        notifyDataSetChanged()
    }
}