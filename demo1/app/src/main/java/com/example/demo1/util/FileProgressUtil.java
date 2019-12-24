package com.example.demo1.util;

import android.app.DownloadManager;

import com.example.demo1.listener.ProgressRequestListener;
import com.example.demo1.requestbody.ProgressRequestBody;

import okhttp3.RequestBody;

public class FileProgressUtil {
    public static ProgressRequestBody addProgressRequestBody(RequestBody requestBody, ProgressRequestListener progressRequestListener) {
        return new ProgressRequestBody(requestBody, progressRequestListener);
    }
}
