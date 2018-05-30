package com.example.v0cn140.eccshop.utility.version;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.example.v0cn140.eccshop.R;

import java.io.File;

public class DialogUtils {

    /**
     * 接口回调
     */
    public interface DialogOnClickListener{
        void btnConfirmClick(Dialog dialog);
        void btnCancelClick(Dialog dialog);
    }

    public DialogOnClickListener updateListener;

    public void setUpdateListener(DialogOnClickListener updateListener) {
        this.updateListener = updateListener;
    }

    public static final void showDialog(Context context,
                                        String title,
                                        String msg,
                                        String OKBtn,
                                        String CancelBtn,
                                        final DialogOnClickListener DialogListener){

        final Dialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCancelable(true);// 可以用“返回键”取消
        dialog.setCanceledOnTouchOutside(false);//
        dialog.show();
        View view = LayoutInflater.from(context).inflate(R.layout.version_update_dialog, null);
        dialog.setContentView(view);

        DialogListener.btnConfirmClick(dialog);
        DialogListener.btnCancelClick(dialog);
    }
}
