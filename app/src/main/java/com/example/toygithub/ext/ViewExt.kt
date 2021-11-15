package com.example.toygithub.ext

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.showToast(context: Context = this, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(context: Context = this.requireContext(), message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}