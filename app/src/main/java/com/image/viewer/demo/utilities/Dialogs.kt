package com.image.viewer.demo.utilities

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.EditText
import com.image.viewer.demo.R
import com.image.viewer.demo.listeners.OnDialogTextChangedListener

fun showRenameDialog(
    context: Context?,
    title: Int?,
    yes: Int?,
    listener: OnDialogTextChangedListener
) {
    val taskEditText = EditText(context)

    val dialog = AlertDialog.Builder(context)
        .setTitle(title!!)
        .setView(taskEditText)
        .setPositiveButton(context?.getString(yes!!)) { _: DialogInterface?, which: Int ->
            val str = taskEditText.text.toString()
            if (!str.isEmpty()) {
                listener.onTextChanged(str)
            } else {
                context.showToast(context?.getString(R.string.message_text_empty))
            }
        }
        .setNegativeButton(context?.getString(R.string.label_cancel), null)
        .create()
    dialog.show()
}
