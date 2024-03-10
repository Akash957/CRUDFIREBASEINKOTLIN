package com.example.phoneappauth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var mobile_edit_text: EditText
    private lateinit var verification_edit_text: EditText
    private lateinit var sendotp_button: Button
    private lateinit var verify_button: Button
    private lateinit var auth: FirebaseAuth

    var verificationID = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mobile_edit_text = findViewById(R.id.mobile_edit_text)
        verification_edit_text = findViewById(R.id.verification_edit_text)
        sendotp_button = findViewById(R.id.sendotp_button)
        verify_button = findViewById(R.id.verify_button)

        auth = FirebaseAuth.getInstance()

        sendotp_button.setOnClickListener {
            sendotp()
        }

        verify_button.setOnClickListener {
            verifyotp()
        }
    }

    private fun sendotp() {
        val phoneAuthOptions = PhoneAuthOptions.newBuilder()
            .setPhoneNumber("+91${mobile_edit_text.text}")
            .setActivity(this)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    Toast.makeText(this@MainActivity, "Verification Success", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Toast.makeText(
                        this@MainActivity, "Verification Failed ${p0.message}", Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onCodeSent(
                    verificationId: String,
                    p1: PhoneAuthProvider.ForceResendingToken) {

                    super.onCodeSent(verificationId, p1)
                    verificationID = verificationId
                    Toast.makeText(this@MainActivity, "SEND SUCCESS", Toast.LENGTH_SHORT).show()
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)
    }
    private fun verifyotp() {
        val otptext = verification_edit_text.text.toString()
        val pnoneAuthCredential = PhoneAuthProvider.getCredential(verificationID, otptext)

        auth.signInWithCredential(pnoneAuthCredential)
            .addOnSuccessListener {
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, CloudFirestore::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
            }
    }

}