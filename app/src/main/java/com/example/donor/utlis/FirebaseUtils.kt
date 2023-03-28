package com.example.donor.utlis

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

object FirebaseUtils {

    var firebaseAuth = FirebaseAuth.getInstance()
    var firebaseUser = FirebaseAuth.getInstance().currentUser
    var firebaseDatabase = FirebaseDatabase.getInstance()
    var firebaseStorage = FirebaseStorage.getInstance()
}