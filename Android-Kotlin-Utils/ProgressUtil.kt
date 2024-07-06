package com.udemy.contact.manager.app.utils

import android.app.Dialog
import android.content.Context
import com.udemy.contact.manager.app.R

class ProgressUtil {

    companion object {
        @Volatile
        private var progressDialog: Dialog? = null

        fun showLoading(context: Context) {
            synchronized(this) {
                if (progressDialog == null) {
                    progressDialog = Dialog(context)
                    progressDialog?.setContentView(R.layout.dialog_progress)
                    progressDialog?.setCancelable(false)
                    progressDialog?.show()
                    progressDialog?.window?.setDimAmount(0.5f)
                    progressDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
                }
            }
        }

        fun hideLoading() {
            progressDialog?.dismiss()
        }
    }
}

/*================================dialog_progress==================================*/
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
