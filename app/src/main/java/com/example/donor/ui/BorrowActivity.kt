package com.example.donor.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.donor.R
import com.example.donor.adapter.BorrowAdapter
import com.example.donor.data.RentInfo
import com.example.donor.databinding.ActivityBorrowBinding
import com.example.donor.utlis.FirebaseUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BorrowActivity : AppCompatActivity() {
    lateinit var binding: ActivityBorrowBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var rentList: ArrayList<RentInfo>
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBorrowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rentList = ArrayList()
        auth = FirebaseUtils.firebaseAuth
        database = FirebaseUtils.firebaseDatabase

        database.reference.child("items")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    rentList.clear()

                    for (snapshot1 in snapshot.children) {
                        val user = snapshot1.getValue(RentInfo::class.java)
                        if(user!!.uid!=FirebaseUtils.firebaseAuth.uid) {
                            rentList.add(user)
                        }
                    }
                    binding.brc.adapter = BorrowAdapter(rentList)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


    }
}