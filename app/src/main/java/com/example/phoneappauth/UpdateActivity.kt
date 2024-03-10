package com.example.phoneappauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class UpdateActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var editText_name: EditText
    private lateinit var editText_email: EditText
    private lateinit var edit_text_password: EditText
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val id = intent.extras?.getString("key_id")
        val name = intent.extras?.getString("key_name")
        val email = intent.extras?.getString("key_email")
        val password = intent.extras?.getString("key_password")

        button = findViewById(R.id.button_singUp)
        editText_name = findViewById(R.id.edit_text_name)
        editText_email = findViewById(R.id.edit_text_email)
        edit_text_password = findViewById(R.id.edit_text_password)

        editText_name.setText(name)
        editText_email.setText(email)
        edit_text_password.setText(password)

        button.setOnClickListener {
            val sName = editText_name.text.toString()
            val sEmail = editText_email.text.toString()
            val sPassword = edit_text_password.text.toString()

            val uid = UUID.randomUUID().toString()

            val map = mapOf(
                "id" to uid,
                "name" to sName,
                "email" to sEmail,
                "password" to sPassword
            )
            db.collection("users").document("$id").update(map)
                .addOnSuccessListener {
                    Toast.makeText(this, "Updated Success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, CloudFirestore::class.java))
                }
                .addOnFailureListener {
                    Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
                }

        }
    }
}
