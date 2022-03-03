package com.supdeweb.androidmusicproject.ui.utils

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.supdeweb.androidmusicproject.R

/**
 * Utils class to display message
 */
object SnackBarHelper {

    /**
     * display an message by type
     * @param parentLayout the parent layout
     * @param message      the message to display
     * @return
     */
    fun displaySnackBar(
        parentLayout: View?,
        message: String?,
        messageType: SnackBarStatusEnum? = null,
        timeLength: Int = Snackbar.LENGTH_LONG,
        anchorView: View? = null,
    ) {
        val snackbar = buildSnackbar(parentLayout!!, message!!, messageType, timeLength, anchorView)

        // show snackbar
        snackbar.show()
    }

    /**
     * To put an action
     */
    fun displaySnackBar(
        parentLayout: View?,
        message: String?,
        messageType: SnackBarStatusEnum? = null,
        timeLength: Int,
        anchorView: View? = null,
        textAction: String,
        doAction: () -> Unit,
    ) {
        val snackbar = buildSnackbar(parentLayout!!, message!!, messageType, timeLength, anchorView)
        snackbar.setAction(textAction) {
            doAction()
        }
        snackbar.show()
    }

    /**
     * Build SnackBar
     */
    private fun buildSnackbar(
        parentLayout: View?,
        message: String?,
        messageType: SnackBarStatusEnum?,
        timeLength: Int,
        anchorView: View?,
    ): Snackbar {
        // create snackbar
        val snackbar = Snackbar.make(parentLayout!!, message!!, timeLength)
        val snackBarView = snackbar.view

        // init text
        val textView = snackBarView.findViewById<TextView>(R.id.snackbar_text)

        // show multiple line
        textView.maxLines = 5

        if (messageType != null) {
            // init colors
            snackBarView.setBackgroundColor(getColorByType(messageType, parentLayout.context))
            snackbar.setActionTextColor(ContextCompat.getColor(parentLayout.context, R.color.white))
        }

        // anchor if present
        anchorView?.let { snackbar.anchorView = it }
        return snackbar
    }

    /**
     * get a color by message type
     *
     * @param messageType the message type
     * @return the color
     */
    private fun getColorByType(messageType: SnackBarStatusEnum, context: Context): Int {
        return when (messageType) {
            SnackBarStatusEnum.ERROR -> ContextCompat.getColor(context, R.color.red)
            else -> ContextCompat.getColor(context, R.color.black)
        }
    }
}
