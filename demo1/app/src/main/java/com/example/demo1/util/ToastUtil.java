package com.example.demo1.util;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

public class ToastUtil {
    private static Toast myToast;

    public static void showToast(final Context context, final String msg) {
        if (myToast == null) {
            Looper.prepare();
            myToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            myToast.setText(msg);
        }
        myToast.show();
    }
}
