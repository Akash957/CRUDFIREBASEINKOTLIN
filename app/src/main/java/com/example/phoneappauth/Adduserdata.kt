package com.example.phoneappauth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class Adduserdata : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var editText_name: EditText
    private lateinit var editText_email: EditText
    private lateinit var editText_password: EditText
    private lateinit var button: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adduserdata)

        button = findViewById(R.id.button_singUp)
        editText_name = findViewById(R.id.edit_text_name)
        editText_email = findViewById(R.id.edit_text_email)
        editText_password = findViewById(R.id.edit_text_password)

        button.setOnClickListener {
            val sName = editText_name.text.toString()
            val sEmail = editText_email.text.toString()
            val sPassword = editText_password.text.toString()

            val uid = UUID.randomUUID().toString()

            val map = hashMapOf(
                "id" to uid,
                "name" to sName,
                "email" to sEmail,
                "password" to sPassword,)
            db.collection("users").document(uid).set(map)
                .addOnSuccessListener {
                    Toast.makeText(this, " Add User Success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, CloudFirestore::class.java))
                }
                .addOnFailureListener {
                    Toast.makeText(this, "failed user", Toast.LENGTH_SHORT).show()
                }

        }

    }
}
