package com.example.donor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.donor.databinding.ActivityMainBinding
import com.example.donor.ui.BorrowActivity
import com.example.donor.ui.NGOActivity
import com.example.donor.ui.RentActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.donate.setOnClickListener {
            startActivity(Intent(this, NGOActivity::class.java))
        }

        binding.rent.setOnClickListener {
            startActivity(Intent(this, RentActivity::class.java))

        }

        binding.brrow.setOnClickListener {
            startActivity(Intent(this, BorrowActivity::class.java))
        }
    }
}



