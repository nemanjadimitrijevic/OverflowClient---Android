package com.dimmythree.overflowclient.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private const val DATE_FORMAT = "dd.MM.yyyy"

    fun Date.format(dateFormat: String): String? {
        return try {
            SimpleDateFormat(dateFormat, Locale.getDefault()).format(this)
        } catch (e: Exception) {
            null
        }
    }

    fun Long.getFormattedDate(): String? {
        return try {
            val date = this * 1000
            SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(date)
        } catch (e: Exception) {
            null
        }
    }

}