package com.dimmythree.overflowclient.ui

import android.view.View
import androidx.fragment.app.Fragment
import com.dimmythree.overflowclient.R
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment() {

    abstract fun startProgress()
    abstract fun stopProgress()
    fun showError(view: View, errorMessage: String? = null) {
        val error = errorMessage ?: getString(R.string.error_unknown)
        Snackbar.make(view, error, Snackbar.LENGTH_LONG).show()
    }

}