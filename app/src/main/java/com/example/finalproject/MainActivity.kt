package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.Query
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database : FirebaseFirestore
    var post_list = ArrayList<Post>()
    private lateinit var recyclerViewAdapter : MainActivityAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        get_information()
        var layoutManager = LinearLayoutManager(this)
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerViewAdapter = MainActivityAdapter(post_list)
        recyclerView.adapter = recyclerViewAdapter
    }

    fun get_information() {
        database.collection("Post").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener { snaphot, exception ->
            if (exception != null) {
                Toast.makeText(this,exception.localizedMessage,Toast.LENGTH_LONG).show()
            } else {
                if (snaphot != null) {
                    if(!snaphot.isEmpty) {
                        val documents = snaphot.documents
                        post_list.clear()
                        for (document in documents) {
                            val email = document.get("email").toString()
                            val comment = document.get("comment").toString()
                            val image = document.get("imageUrl").toString()
                            val download_post = Post(email, comment, image)
                            post_list.add(download_post)
                        }
                    }
                    recyclerViewAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout){
            mAuth.signOut()
            val intent = Intent(this@MainActivity, Login::class.java)
            finish()
            startActivity(intent)

        }
        if(item.itemId == R.id.upload){
            val intent = Intent(this, UploadPhoto::class.java)
            startActivity(intent)

        }
        if(item.itemId == R.id.userProfile){
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}