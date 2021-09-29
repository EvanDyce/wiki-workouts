package com.evan.workoutapp.utils;

import android.app.Activity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DialogMessage {
    public static void Failure(Activity activity, String message) {
        new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(message)
                .show();
    }

    public static void Success(Activity activity, String message) {
        new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(message)
                .show();
    }

    public static void Normal(Activity activity, String message) {
        new SweetAlertDialog(activity, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(message)
                .show();
    }

}
