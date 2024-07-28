package com.udemy.journal.app.utils

import android.app.Dialog
import android.app.Activity
import android.util.Log
import com.udemy.journal.app.R

class ProgressUtils {
    companion object {
        @Volatile
        private var progressDialog: Dialog? = null

        fun showLoading(activity: Activity) {
            synchronized(this) {
                if (progressDialog == null) {
                    progressDialog = Dialog(context)
                    progressDialog?.setContentView(R.layout.dialog_progress)
                    progressDialog?.setCancelable(false)
                    progressDialog?.window?.setDimAmount(0.5f)
                    progressDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
                }
                progressDialog?.show()
                Log.d("--dialog--", "show")
            }
        }

        fun hideLoading() {
            progressDialog?.dismiss()
            progressDialog = null
            Log.d("--dialog--", "dismiss")
        }
    }
}

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ProgressBar
        android:id="@+id/progressBar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:indeterminateTint="@color/black"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
