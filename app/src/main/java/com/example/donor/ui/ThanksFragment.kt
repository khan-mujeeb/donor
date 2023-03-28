package com.example.donor.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.donor.MainActivity
import com.example.donor.databinding.FragmentThanksBinding


class ThanksFragment : Fragment() {

    private lateinit var binding: FragmentThanksBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentThanksBinding.inflate(layoutInflater)


        return binding.root
    }

}