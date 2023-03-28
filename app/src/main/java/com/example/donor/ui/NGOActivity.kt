package com.example.donor.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.donor.R
import com.example.donor.adapter.NGOAdapter
import com.example.donor.databinding.ActivityMainBinding
import com.example.donor.databinding.ActivityNgoactivityBinding
import com.example.donor.utlis.NGOData

class NGOActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNgoactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNgoactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = NGOData.ngoList()
        binding.ngoRC.adapter = NGOAdapter(data)
    }
}