package com.andela.logfooddiary.data

import com.google.firebase.database.FirebaseDatabase


class Repository {

    val database = FirebaseDatabase.getInstance()
    val myRef = database.reference

    fun addToDatabase(food: Food) {
        val key = myRef.child("food").push().key
        key?.let {
            food.id= it
            myRef.child(it).setValue(food)
        }
    }
}