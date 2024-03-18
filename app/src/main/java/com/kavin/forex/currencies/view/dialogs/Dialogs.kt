package com.kavin.forex.currencies.view.dialogs

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kavin.forex.R

/**
 * class to show dialogs
 */
object Dialogs {

    fun showErrorDialogWithRetry(
        context: Context,
        retryFunction: () -> Unit,
        closeFunction: () -> Unit
    ) {
        MaterialAlertDialogBuilder(context)
            .setTitle(context.resources.getString(R.string.error_title))
            .setMessage(context.resources.getString(R.string.error_message_retry))
            .setPositiveButton(context.resources.getString(R.string.retry)) { _, _ ->
                retryFunction()
            }.setNegativeButton(context.resources.getString(R.string.close)) { _, _ ->
                closeFunction()
            }
            .show()
    }

    fun showErrorDialog(context: Context, message: String?) {
        showInfoDialog(
            context,
            context.resources.getString(R.string.error_title),
            message ?: context.resources.getString(R.string.error_title)
        )
    }

    private fun showInfoDialog(context: Context, title: String, message: String) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(context.resources.getString(R.string.ok)) { _, _ ->
                // Respond to positive button press, nothing to do here
            }
            .show()
    }
}