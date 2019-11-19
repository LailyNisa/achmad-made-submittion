package com.achmad.madeacademy.moviecataloguemvp.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.achmad.madeacademy.moviecataloguemvp.R;

public class CommonUtils {
    private static Dialog progressDialog;
    private static final String TAG = "CommonUtils";

    public CommonUtils() {
    }

    public static Dialog showLoading(Context context) {
        progressDialog = new Dialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static void hideLoading() {
        if (progressDialog == null) {
            return;
        }
        progressDialog.dismiss();
    }
}
