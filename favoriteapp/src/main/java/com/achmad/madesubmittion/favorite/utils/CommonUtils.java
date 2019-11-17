package com.achmad.madesubmittion.favorite.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.achmad.madesubmittion.favorite.R;

public class CommonUtils {
    private static final String TAG = "CommonUtils";
    private static ProgressDialog progressDialog;

    public CommonUtils() {
    }

    public static ProgressDialog showLoading(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
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
