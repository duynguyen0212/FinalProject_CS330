package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class Profile : AppCompatActivity() {
    private lateinit var database: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    var post_list = ArrayList<Post>()
    private lateinit var profileRecyclerViewAdapter: ProfileAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()
        val userEmail = findViewById<TextView>(R.id.profile_email)

        if (intent.getStringExtra("name") == "currentUser") {
            userEmail.setText(auth.currentUser!!.email.toString())
            var rec_email = auth.currentUser!!.email.toString()
            get_information(rec_email)

            var recyclerView = findViewById<RecyclerView>(R.id.profileRecyclerView)
            recyclerView.setHasFixedSize(true)
            var layoutManager = GridLayoutManager(this, 3)
            recyclerView.layoutManager = layoutManager
            profileRecyclerViewAdapter = ProfileAdapter(post_list)
            recyclerView.adapter = profileRecyclerViewAdapter

        }


    }

    fun get_information(rec_email: String) {
        database.collection("Post").orderBy(
            "date", Query.Direction.DESCENDING
        ).addSnapshotListener { snaphot, exception ->
            if (exception != null) {
                Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (snaphot != null) {
                    if (!snaphot.isEmpty) {
                        val documents = snaphot.documents
                        post_list.clear()
                        for (document in documents) {
                            if (rec_email == document.get("email").toString()) {
                                val email = document.get("email").toString()
                                val comment = document.get("comment").toString()
                                val image = document.get("imageUrl").toString()
                                val date = document.get("date").toString()
                                val download_post = Post(email, comment, image, date)
                                post_list.add(download_post)
                            }
                        }
                    }
                    profileRecyclerViewAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}