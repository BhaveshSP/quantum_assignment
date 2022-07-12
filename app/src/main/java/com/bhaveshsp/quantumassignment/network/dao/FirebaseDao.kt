package com.bhaveshsp.quantumassignment.network.dao

import com.bhaveshsp.quantumassignment.models.User
import com.bhaveshsp.quantumassignment.utils.USER_DATABASE
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

class FirebaseDao {
    companion object {
        private const val TAG = "TESTING_DAO"
        private val reference = FirebaseDatabase.getInstance().getReference(USER_DATABASE)

        fun createUser(phoneNumber: String) {
            reference.child(phoneNumber).setValue(true)
        }
        fun setUserData(user: User, phoneNumber: String): Task<Void?> {
            return reference.child(phoneNumber).setValue(user)
        }
        fun userDatabaseReference(phoneNumber: String) : Task<DataSnapshot>{
            return reference.child(phoneNumber).get()
        }



    }
}