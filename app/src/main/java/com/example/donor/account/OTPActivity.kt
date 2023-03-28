package com.example.donor.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.donor.MainActivity
import com.example.donor.databinding.ActivityOtpactivityBinding
import com.example.donor.utlis.FirebaseUtils
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OTPActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpactivityBinding
    private lateinit var dialog: androidx.appcompat.app.AlertDialog
    private lateinit var verificationId: String
    private lateinit var builder: AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // loading dialog
        alterDialog()
        dialog.show()


        val number = intent.getStringExtra("number").toString()
        val name = intent.getStringExtra("name").toString()
        val phonenumber = "+91"+number
        println("number is $phonenumber")
        // verification
        val options = PhoneAuthOptions.newBuilder(FirebaseUtils.firebaseAuth)
            .setPhoneNumber(phonenumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    dialog.dismiss()
                    Toast.makeText(this@OTPActivity,"Verification Failed $p0", Toast.LENGTH_LONG).show()
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)
                    dialog.dismiss()
                    verificationId = p0
                    println("mujeeb $verificationId")
                }

            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)

        binding.btnContinue.setOnClickListener {
            val otp = binding.etNumber.text.toString()
            if(otp.isNotEmpty()) {
                dialog.show()

                // phone number verification
                val credential = PhoneAuthProvider.getCredential(verificationId,otp)
                FirebaseUtils.firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
                    if(it.isSuccessful){
                        dialog.dismiss()
                        val database = FirebaseUtils.firebaseDatabase
                    val uid = it.result.user!!.uid
                        val user = com.example.donor.data.UserInfo(
                            name = name,
                            phonenumber = number,
                            uid = uid
                        )
                        database.reference.child("users").child(user.uid).setValue(user).addOnSuccessListener {
                            startActivity(Intent(this, MainActivity::class.java))
                        }
                    }else{
                        dialog.dismiss()
                        Toast.makeText(this@OTPActivity,"${it.exception}", Toast.LENGTH_LONG).show()
                    }
                }

            }else {
                Toast.makeText(this@OTPActivity,"Enter OTP", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun alterDialog() {
        builder = AlertDialog.Builder(this)
        builder.setMessage("Loading..")
        builder.setCancelable(false)
        dialog = builder.create()
    }


}