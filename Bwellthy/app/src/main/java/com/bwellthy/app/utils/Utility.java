package com.bwellthy.app.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Utility {

    public static boolean DEBUG = Boolean.FALSE;
    public static ProgressDialog progressDialog;

    public static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return Math.round(px);
    }

    public static void showProgressDialog(Context context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
        }
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    public static void dismissProgessDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
