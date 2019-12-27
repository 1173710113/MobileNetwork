package com.example.demo1.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.demo1.DiscussionByCourseActivity;
import com.example.demo1.LoginActivity;
import com.example.demo1.R;
import com.example.demo1.dialog.CustomDialog;
import com.google.android.material.navigation.NavigationView;
import com.hjq.toast.ToastUtils;

import static android.content.Context.MODE_PRIVATE;

public class MyNavView {

    public static void initNavView(final Activity activity, final Context context, NavigationView navigationView) {
        navigationView.setCheckedItem(R.id.student_main_nav_info);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.student_main_nav_exit:
                        final CustomDialog dialog = new CustomDialog(context);
                        dialog.setTitle("提示").setContent("确认退出？").setCancelListener(new CustomDialog.IOnCancelListener() {
                            @Override
                            public void onCancel() {
                                dialog.dismiss();
                            }
                        }).setConfirmListener(new CustomDialog.IOnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                SharedPreferences pref = context.getSharedPreferences("userInfo", MODE_PRIVATE);
                                pref.edit().clear().apply();
                                dialog.dismiss();
                                Intent intent = new Intent(context, LoginActivity.class);
                                context.startActivity(intent);
                                ToastUtils.show("已退出");
                                activity.finish();
                            }
                        }).show();
                }
                return true;
            }
        });
    }
}
