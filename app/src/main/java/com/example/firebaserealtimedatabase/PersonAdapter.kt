package com.example.firebaserealtimedatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PersonAdapter(private val dataSet: List<Person>) :
    RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.tVName)
        val textViewAge: TextView = view.findViewById(R.id.tVAge)
        val textViewState: TextView = view.findViewById(R.id.tVState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_person, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = dataSet[position]
        holder.textViewName.text = "Name: ${person.name}"
        holder.textViewAge.text = "Age: ${person.age}"
        holder.textViewState.text = "State: ${person.state}"
    }

    override fun getItemCount() = dataSet.size
}
