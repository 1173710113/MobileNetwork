package com.example.demo1.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.demo1.DiscussionActivity;
import com.example.demo1.LoginActivity;
import com.example.demo1.R;
import com.example.demo1.StudentMainActivity;
import com.example.demo1.TeacherMainActivity;
import com.example.demo1.dialog.CustomDialog;
import com.example.demo1.dialog.EnrollDialog;
import com.google.android.material.navigation.NavigationView;
import com.hjq.toast.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class MyNavView {

    public static void initNavView(final Activity activity, final Context context, final NavigationView navigationView) {
        View headerLayout = (View)navigationView.inflateHeaderView(R.layout.main_nav_header);
        TextView headerName= (TextView) headerLayout.findViewById(R.id.main_nav_header_name);
        TextView headerId = (TextView)headerLayout.findViewById(R.id.main_nav_header_id);
        headerName.setText(context.getSharedPreferences("userInfo", MODE_PRIVATE).getString("name", "ERROR"));
        headerId.setText(context.getSharedPreferences("userInfo",MODE_PRIVATE).getString("id", "ERROR"));
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
                        break;
                    case R.id.student_main_nav_talk:
                        Intent intent = new Intent(context, DiscussionActivity.class);
                        context.startActivity(intent);
                        break;
                    case R.id.main_nav_course:
                        Log.e("NAV", "点击课程");
                        Log.e("NAV", context.toString());
                        String type = context.getSharedPreferences("userInfo", MODE_PRIVATE).getString("type", null);
                        Intent intent2 = null;
                        Log.e("NAV", "type" + type);
                        switch (type){
                            case "教师":
                                intent2 = new Intent(context, TeacherMainActivity.class);
                                break;
                            case "学生":
                                    intent2 = new Intent(context, StudentMainActivity.class);
                                    break;
                        }
                        if(intent2 != null) {
                            context.startActivity(intent2);
                        }
                        break;
                    case R.id.student_main_nav_info:
                        final EnrollDialog dialog1 = new EnrollDialog(context);
                        dialog1.setHint("请输入姓名");
                        dialog1.setTitle("修改信息");
                        String name = context.getSharedPreferences("userInfo", MODE_PRIVATE).getString("name", null);
                        dialog1.setText(name);
                        dialog1.setOnCancelListener(new EnrollDialog.IOnCancelListener() {
                            @Override
                            public void onCancel() {
                                dialog1.dismiss();
                            }
                        }).setOnConfirmListener(new EnrollDialog.IOnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                final String name = dialog1.getCode();
                                if(ValidateUtil.isEmpty(name)) {
                                    ToastUtils.show("不能为空");
                                    return;
                                }
                                String id = context.getSharedPreferences("userInfo", MODE_PRIVATE).getString("id", null);
                                if(ValidateUtil.isEmpty(id)){
                                    ToastUtils.show("出错");
                                    return;
                                }
                                String url = "http://10.0.2.2:8081/mobile/user/updatename";
                                RequestBody requestBody = new MultipartBody.Builder()
                                        .setType(MultipartBody.FORM)
                                        .addFormDataPart("name", name)
                                        .addFormDataPart("id", id)
                                        .build();
                                Request request = new Request.Builder().url(url).post(requestBody).build();
                                OkHttpClient client = new OkHttpClient.Builder()
                                        .connectTimeout(100, TimeUnit.MILLISECONDS)
                                        .build();
                                client.newCall(request).enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        ToastUtils.show("修改失败");
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                            SharedPreferences.Editor editor = context.getSharedPreferences("userInfo", MODE_PRIVATE).edit();
                                            editor.putString("name", name);
                                            editor.apply();
                                            dialog1.dismiss();
                                            ToastUtils.show("修改成功");
                                    }
                                });

                            }
                        }).show();
                        break;
                    case R.id.main_nav_pass:
                        final EnrollDialog dialog3 = new EnrollDialog(context);
                        dialog3.setTitle("修改密码");
                        dialog3.setHint("请输入密码");
                        dialog3.setVisible(false);
                        dialog3.setOnCancelListener(new EnrollDialog.IOnCancelListener() {
                            @Override
                            public void onCancel() {
                                dialog3.dismiss();
                            }
                        });
                        dialog3.setOnConfirmListener(new EnrollDialog.IOnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                final String pass = dialog3.getCode();
                                if(ValidateUtil.isEmpty(pass)) {
                                    ToastUtils.show("不能为空");
                                    return;
                                }
                                String id = context.getSharedPreferences("userInfo", MODE_PRIVATE).getString("id", null);
                                if(ValidateUtil.isEmpty(id)){
                                    ToastUtils.show("出错");
                                    return;
                                }
                                String url = "http://10.0.2.2:8081/mobile/user/update/pass";
                                RequestBody requestBody = new MultipartBody.Builder()
                                        .setType(MultipartBody.FORM)
                                        .addFormDataPart("id", id)
                                        .addFormDataPart("pass", pass)
                                        .build();
                                Request request = new Request.Builder().url(url).post(requestBody).build();
                                OkHttpClient client = new OkHttpClient.Builder()
                                        .connectTimeout(100, TimeUnit.MILLISECONDS)
                                        .build();
                                client.newCall(request).enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        ToastUtils.show("修改失败");
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        SharedPreferences.Editor editor = context.getSharedPreferences("userInfo", MODE_PRIVATE).edit();
                                        editor.putString("password", pass);
                                        editor.apply();
                                        dialog3.dismiss();
                                        ToastUtils.show("修改成功");
                                    }
                                });
                            }
                        }).show();
                        break;
                }
                return true;
            }
        });
    }

}
