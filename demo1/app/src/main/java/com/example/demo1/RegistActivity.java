package com.example.demo1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.alibaba.fastjson.JSONObject;
import com.example.demo1.util.AES;
import com.example.demo1.util.HttpUtil;
import com.hjq.toast.ToastUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegistActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
    }

    public void Regist(View view) {
        MaterialEditText idEdit = (MaterialEditText)findViewById(R.id.user_id);
        MaterialEditText passwordEdit = (MaterialEditText)findViewById(R.id.user_password);
        MaterialEditText nameEdit = (MaterialEditText)findViewById(R.id.user_name);
        String id = idEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        String name = nameEdit.getText().toString();
        Spinner sexSpinner = (Spinner)findViewById(R.id.user_sex);
        Spinner typeSpiner = (Spinner)findViewById(R.id.user_type);
        String sex = (String)sexSpinner.getSelectedItem();
        String type = (String)typeSpiner.getSelectedItem();
        if(!idEdit.isCharactersCountValid() || !passwordEdit.isCharactersCountValid() || !nameEdit.isCharactersCountValid()) {
            ToastUtils.show("请正确输入");
            return;
        }
        try {
            JSONObject obj = new JSONObject();
            obj.put("userId", id);
            obj.put("password", password);
            obj.put("type", type);
            obj.put("name", AES.Encrypt(name, AES.sKey));
            obj.put("sex", sex);
            obj.put("iconpath", "123457");
            String url = "http://10.0.2.2:8081/mobile/user/add";
            HttpUtil.sendHttpRequest(url, obj, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    ToastUtils.show("注册失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    ToastUtils.show("注册成功");
                    Intent intent = new Intent(RegistActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
