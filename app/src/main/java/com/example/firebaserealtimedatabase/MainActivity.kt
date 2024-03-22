package com.example.firebaserealtimedatabase

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var adapter: PersonAdapter
    private val users = ArrayList<Person>()

    val changeListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.hasChildren()) {
                // has children
                var count = snapshot.childrenCount
                users.clear()
                for (child in snapshot.children) {
                    val holdData = child.getValue(Person::class.java)
                    users.add(holdData!!)
                }
            }
        }


        override fun onCancelled(error: DatabaseError) {
            Log.e("MainActivity", "Database error: ${error.message}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val editTextName = findViewById<EditText>(R.id.TxtName)
        val editTextAge = findViewById<EditText>(R.id.TxtAge)
        val spinnerStates = findViewById<Spinner>(R.id.spinState)
        val buttonAdd = findViewById<Button>(R.id.btnAdd)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PersonAdapter(users)
        recyclerView.adapter = adapter

        // Initialize Firebase
        database = FirebaseDatabase.getInstance().reference.child("test4")
        database.addValueEventListener(changeListener)

        // Populate spinner with states
        val states = arrayOf("Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware",
            "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky",
            "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri",
            "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York",
            "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island",
            "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington",
            "West Virginia", "Wisconsin", "Wyoming")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, states)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerStates.adapter = adapter

        buttonAdd.setOnClickListener {
            val name = editTextName.text.toString()
            val age = editTextAge.text.toString().toIntOrNull() ?: 0
            val state = spinnerStates.selectedItem.toString()

            val id = database.push().key
            val person = Person(id ?: "", name, age, state)
            database.child(id ?: "").setValue(person)

            // Clear input fields
            editTextName.text.clear()
            editTextAge.text.clear()

            database.addListenerForSingleValueEvent(changeListener)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        database.removeEventListener(changeListener)
    }
}

