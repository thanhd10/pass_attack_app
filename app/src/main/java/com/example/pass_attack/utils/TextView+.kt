package com.example.pass_attack.utils

import android.util.Log
import android.widget.TextView
import java.util.*

private const val TEXT_VIEW_EXTENSIONS = "TextView+"


fun TextView.setFormattedMeasurementValue(placeHolderId: Int, value: Double) {
    try {
        this.text = String.format(this.resources.getString(placeHolderId), String.format("%.6f", value))
    } catch (e: IllegalFormatException) {
        Log.d(TEXT_VIEW_EXTENSIONS, "Unable to format 'Double' value!")
    }
}

fun TextView.setFormattedTimeValue(value: Double) {
    try {
        this.text = String.format(Locale.US, "%.2f", value)
    } catch (e: IllegalFormatException) {
        Log.d(TEXT_VIEW_EXTENSIONS, "Unable to format 'Double' value!")
    }
}