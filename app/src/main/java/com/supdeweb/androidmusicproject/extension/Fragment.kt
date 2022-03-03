/*
 * Copyright 2021 OPTIMETRIKS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.supdeweb.androidmusicproject.extension

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.supdeweb.androidmusicproject.ui.utils.SnackBarHelper
import com.supdeweb.androidmusicproject.ui.utils.SnackBarStatusEnum

/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun Fragment.showSnackbar(
    message: String,
    messageType: SnackBarStatusEnum? = null,
    timeLength: Int = Snackbar.LENGTH_LONG,
    anchorView: View? = null,
) {
    activity?.let {
        val view = it.findViewById<View>(android.R.id.content)
        SnackBarHelper.displaySnackBar(view, message, messageType, timeLength, anchorView)
    }
}

/**
 * Transforms static java function Snackbar.make().setAction() to an extension function on View.
 */
fun Fragment.showActionSnackbar(
    snackbarText: String,
    textAction: String,
    doAction: () -> Unit,
    messageType: SnackBarStatusEnum? = null,
    timeLength: Int = Snackbar.LENGTH_LONG,
    anchorView: View? = null,
) {
    activity?.let {
        val view = it.findViewById<View>(android.R.id.content)
        SnackBarHelper.displaySnackBar(view,
            snackbarText,
            messageType,
            timeLength,
            anchorView,
            textAction,
            doAction)
    }
}
