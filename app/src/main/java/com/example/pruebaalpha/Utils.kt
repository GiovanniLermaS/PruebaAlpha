package com.example.pruebaalpha

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText

fun EditText.setWarning(message: String, context: Context): Boolean {
    return if (!this.text.isEmpty()) false
    else {
        this.requestFocus()
        val icWarnings = ContextCompat.getDrawable(context, R.drawable.ic_warning)
        icWarnings!!.setBounds(0, 0, icWarnings.intrinsicWidth, icWarnings.intrinsicHeight)
        this.setError(message, icWarnings)
        true
    }
}

fun TextInputEditText.setRequestWarning(context: Context): Boolean {
    return this.setWarning(context.getString(R.string.requiredField), context)
}

fun View.setWarningsRequest(): Boolean {
    return if (this is ViewGroup) {
        this.evaluateWarningsView()
    } else false
}

fun ViewGroup.evaluateWarningsView(): Boolean {
    val result = false
    this.invalidate()
    (0 until this.childCount)
        .takeWhile { this.getChildAt(it).visibility != View.GONE }
        .forEach {
            if (this.getChildAt(it) is TextInputEditText) {
                if ((this.getChildAt(it) as TextInputEditText).setRequestWarning(this.context)) return true
            } else if (this.getChildAt(it) is ViewGroup) {
                if (this.getChildAt(it).setWarningsRequest()) return true
            }
        }
    return result
}