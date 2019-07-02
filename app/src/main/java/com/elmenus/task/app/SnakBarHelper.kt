package com.elmenus.task.app

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

object SnakBarHelper {
    fun retry(view: View, listener: View.OnClickListener) {
        val snackbar = Snackbar
            .make(view, "No internet connection!", Snackbar.LENGTH_LONG)
            .setAction("RETRY", listener)

        // Changing message text color
        snackbar.setActionTextColor(Color.RED)

        // Changing action button text color
        val sbView = snackbar.view
        val textView = sbView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setTextColor(Color.YELLOW)
        snackbar.show()

//        Snackbar retry = Snackbar.make(view, R.string.connection_lost, Snackbar.LENGTH_INDEFINITE);
//        retry.setAction("Retry", it -> {
//            runnable.run();
//            retry.dismiss();
//        });
//        retry.show();
    }
}