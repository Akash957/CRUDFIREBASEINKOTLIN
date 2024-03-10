package com.example.phoneappauth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class CloudFirestore : AppCompatActivity(), SetOnStudentClickListener {
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var listView: ListView
    private lateinit var userStudentsAdapter: TestAdpater
    private val db = FirebaseFirestore.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cloud_firestore)

        floatingActionButton = findViewById(R.id.floatingActionButton)
        listView = findViewById(R.id.listview)
        showStudents()
        val id = UUID.randomUUID().toString()
        getStudent(id)

        showStudents()

        floatingActionButton.setOnClickListener {
            val intent = Intent(this, Adduserdata::class.java)
            startActivity(intent)
        }
    }
    private fun showStudents() {
        db.collection("users").get()
            .addOnSuccessListener {
                val userArray = ArrayList<StudentModel>()
                for (document in it) {
                    val data = document.toObject(StudentModel::class.java)
                    userArray.add(data) }
                userStudentsAdapter = TestAdpater(userArray, this, this)
                listView.adapter = userStudentsAdapter
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                return@addOnSuccessListener
            }
            .addOnFailureListener {
                Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
                return@addOnFailureListener
            }
    }
    override fun onUpdateClick(student: StudentModel) {
        val intent = Intent(this, UpdateActivity::class.java)
        intent.putExtra("key_id", student.id.toString())
        intent.putExtra("key_name", student.name.toString())
        intent.putExtra("key_email", student.email.toString())
        intent.putExtra("key_password", student.password.toString())
        startActivity(intent)
    }
    override fun onDeleteClick(student: StudentModel) {
        deleteStudent(student.id.toString())
    }
    private fun deleteStudent(id: String) {
        db.collection("users").document(id).delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Delete Success", Toast.LENGTH_SHORT).show()
                showStudents()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show()
            }
    }
    private fun getStudent(id: String) {
        db.collection("users").document(id).get()
            .addOnSuccessListener {
            }
            .addOnFailureListener {

            }
    }
}