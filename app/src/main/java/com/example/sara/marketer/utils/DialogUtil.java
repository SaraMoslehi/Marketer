package com.example.sara.marketer.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.Window;


public class DialogUtil {
    public static Dialog showDialog(Context mContext, int layoutID) {
        final Dialog mDialog = new Dialog(mContext);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displaymetrics);

        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;


        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(layoutID);
        mDialog.getWindow()

                .setLayout(width,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        mDialog.show();

        return mDialog;

    }

    public static Dialog showDialogFilter(Context mContext, int layoutID) {
        final Dialog mDialog = new Dialog(mContext);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displaymetrics);

        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;


        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(layoutID);
        mDialog.getWindow()

                .setLayout(width,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        mDialog.show();

        return mDialog;

    }


}
