package com.example.androidmidterm.util

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showErrorSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).apply {
        setBackgroundTint(Color.RED)
        show()
    }
}

fun View.showSuccessSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).apply {
        setBackgroundTint(Color.GREEN)
        show()
    }
}