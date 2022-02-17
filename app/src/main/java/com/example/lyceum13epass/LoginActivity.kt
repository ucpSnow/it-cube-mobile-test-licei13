package com.example.lyceum13epass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    var b_login: Button? = null
    var et_login: EditText? = null
    var et_pass: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        b_login = findViewById(R.id.b_login)
        et_login = findViewById(R.id.et_login_email)
        et_pass = findViewById(R.id.et_login_pass)
        b_login?.setOnClickListener {
            when {
                TextUtils.isEmpty(et_login?.text.toString().trim{it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please, enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_pass?.text.toString().trim{it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please, enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email: String = et_login?.text.toString().trim{it <= ' '}
                    val password: String = et_pass?.text.toString().trim{it <= ' '}

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener{task ->
                            if (task.isSuccessful){
                                Toast.makeText(
                                    this@LoginActivity,
                                    "You were logged in successfully.",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra(
                                    "user_id",
                                    FirebaseAuth.getInstance().currentUser!!.uid
                                )
                                intent.putExtra("email_id", FirebaseAuth.getInstance().currentUser!!.email)
                                intent.putExtra("name_id",FirebaseAuth.getInstance().currentUser!!.displayName)
                                intent.putExtra("date_id",FirebaseAuth.getInstance().currentUser!!.photoUrl)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }

            }
        }


    }
}