package com.example.demo1.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;
//用于在非主线程更新UI
public abstract class UIUpdateUtilImp implements UIUpdateUtil {
    private static final int UPDATE = 1;

    private static class UIHandler extends Handler {
        private final WeakReference<UIUpdateUtilImp> mUIUpdateUtilImpWeakReference;

        public UIHandler(Looper looper, UIUpdateUtilImp uiUpdateUtilImp) {
            super(looper);
            mUIUpdateUtilImpWeakReference = new WeakReference<UIUpdateUtilImp>(uiUpdateUtilImp);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE:
                    UIUpdateUtilImp uiUpdateUtilImp = mUIUpdateUtilImpWeakReference.get();
                    if (uiUpdateUtilImp != null) {
                        uiUpdateUtilImp.onUIUpdate();
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    private final Handler mHandler = new UIHandler(Looper.getMainLooper(), this);

    @Override
    public void onUpdate() {
        Message msg = new Message();
        msg.what = UPDATE;
        mHandler.sendMessage(msg);
    }

    public abstract void  onUIUpdate();
}
