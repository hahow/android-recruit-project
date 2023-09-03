package com.hahow.ui.dialog


import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AlertDialog.Builder
import `in`.hahow.android_recruit_project.R


object AlertDialogFactory {
    fun showLoadingDialog(context: Context): AlertDialog {
        return Builder(context).apply {
            setCancelable(false) // 防止使用者按返回鍵或點擊對話框外部取消對話框
            setView(R.layout.dialog_loading) // 在此處設置自訂的佈局以顯示Loading UI
        }.create()
    }

    fun showAlertDialog(context: Context, message: String): AlertDialog {
        return Builder(context).apply {
            setMessage(message)
            setPositiveButton(R.string.alert_dialog_positive_button_text) { dialog, _ ->
                dialog.dismiss()
            }
        }.create()
    }
}