package com.example.finalproject

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class SignUp : AppCompatActivity() {
    private lateinit var  editName: EditText
    private lateinit var  editEmail: EditText
    private lateinit var  editPassword: EditText
    private lateinit var  btnSignUp: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabaseRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        mAuth = FirebaseAuth.getInstance()
        mDatabaseRef = Firebase.database.reference

        editName = findViewById(R.id.edit_name)
        editEmail = findViewById(R.id.edit_email)
        editPassword = findViewById(R.id.edit_password)
        btnSignUp= findViewById(R.id.btnSignUp)

        btnSignUp.setOnClickListener{
            val email = editEmail.text.toString().trim()
            val password = editPassword.text.toString().trim()
            val name = editName.text.toString()
            if(email.isEmpty()||password.isEmpty()){
                Toast.makeText(this@SignUp, "Please fill out email and passcode", Toast.LENGTH_SHORT).show()
            }else{signUp(email, password, name)}
        }
    }
    private fun signUp(email:String, password:String, name:String){
        try {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {
                        addUser(name, email, mAuth.currentUser?.uid!!)
                        val intent = Intent(this@SignUp, MainActivity::class.java)
                        finish()
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@SignUp, "Error " + task.exception, Toast.LENGTH_LONG).show()
                        print(task.exception)
                    }
                }
        }catch(e:Exception){

        }
    }

    private fun addUser(name:String, email:String, uid:String){
//        mDatabaseRef = FirebaseDatabase.getInstance().reference
        mDatabaseRef.child("user").child(uid).setValue(User(name, email, uid))
    }
}