package com.elmenus.task.app

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

object SnakBarHelper {
    fun retry(view: View, listener: View.OnClickListener) {
        val snackbar = Snackbar
            .make(view, "No internet connection!", Snackbar.LENGTH_LONG)
            .setActionTextColor(Color.RED)
            .setAction("RETRY", listener)

        // Changing action button text color
        val sbView = snackbar.view
        val textView = sbView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setTextColor(Color.YELLOW)
        snackbar.show()
    }
}