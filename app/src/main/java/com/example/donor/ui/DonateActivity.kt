package com.example.donor.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.example.donor.MainActivity
import com.example.donor.R
import com.example.donor.account.SignInActivity
import com.example.donor.databinding.ActivityDonateBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class DonateActivity : AppCompatActivity() {
    lateinit var binding: ActivityDonateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.push.setOnClickListener {
            val view : View = layoutInflater.inflate(R.layout.fragment_thanks,null)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(view)
            dialog.setCancelable(true)
            dialog.show()

            Handler().postDelayed({
                val intent= Intent(this, MainActivity::class.java)
                startActivity(intent);
            },3000)

        }
    }
}