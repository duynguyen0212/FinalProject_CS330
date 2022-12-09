package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    private lateinit var  editEmail:EditText
    private lateinit var  editPassword:EditText
    private lateinit var  btnLogin:Button
    private lateinit var  btn_SignUp:Button

    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        editEmail = findViewById(R.id.edit_email)
        editPassword = findViewById(R.id.edit_password)
        btnLogin = findViewById(R.id.btn_login)
        btn_SignUp= findViewById(R.id.btn_signUp)

        btn_SignUp.setOnClickListener{
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener{
            val email = editEmail.text.toString().trim()
            val password = editPassword.text.toString().trim()
            if(email.isEmpty()||password.isEmpty()){
                Toast.makeText(this@Login, "Please fill out email and passcode", Toast.LENGTH_SHORT).show()
            }else{login(email, password)}
        }

    }
    private fun login(email:String, password:String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@Login, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@Login, "Error" + task.exception, Toast.LENGTH_LONG).show()
                }
            }
    }

    //Start main activity (contact list) if user already signed in
    override fun onStart() {
        super.onStart()
        val user = mAuth.currentUser
        if(user!=null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}