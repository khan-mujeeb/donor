package com.example.donor.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.donor.MainActivity
import com.example.donor.R
import com.example.donor.databinding.ActivityProfileBinding
import com.example.donor.databinding.ActivitySignInBinding
import com.example.donor.utlis.FirebaseUtils

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (FirebaseUtils.firebaseUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // go to otp activity
        binding.getOtp.setOnClickListener {

            if (checkEditText()) {
                val intent = Intent(this, OTPActivity::class.java)
//                Toast.makeText(this,"Verification Failed ${getNumber()}",Toast.LENGTH_SHORT).show()
                intent.putExtra("number", getNumber())
                intent.putExtra("name", getName())
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "enter correct number", Toast.LENGTH_SHORT).show()
            }
        }

    }

    // function to check edittext is empty or not
    fun checkEditText(): Boolean {

        if (binding.one.text.toString().isNotEmpty() && binding.one.text.toString().length == 10) {
            return true
        }
        return false
    }

    // getter to get number
    fun getNumber(): String {
        return binding.one.text.toString()
    }

    fun getName(): String {
        return binding.nameEdtxt.text.toString()
    }


}
