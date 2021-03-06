package com.example.demo1.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    /***
     * 向服务器发送json,可以通过复写callback获得服务器响应
     * @param url
     * @param json
     * @param callback
     */
    public static void sendHttpRequest(String url, JSONObject json, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.MILLISECONDS)
                .build();
        MediaType mtype = MediaType.parse("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.create(mtype,""+json.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try {
            client.newCall(request).enqueue(callback);
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void sendHttpRequest(String url, JSONArray json, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.MILLISECONDS)
                .build();
        MediaType mtype = MediaType.parse("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.create(mtype,""+json.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try {
            client.newCall(request).enqueue(callback);
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 发送无JSON串的Http请求
     * @param url
     * @param callback
     */
    public static void sendHttpRequest(String url, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.MILLISECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 上传文件用的
     * @param url
     * @param requestBody
     * @param callback
     */
    public static void postFile(String url, RequestBody requestBody, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.MILLISECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);

    }

}
