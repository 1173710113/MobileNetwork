package com.example.demo1.listener;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

public abstract class UIHttpResponseToastListener implements HttpResponseToastListener {
    private static final int SHOW_TOAST = 1;

    private static class UIHandler extends Handler {
        private final WeakReference<UIHttpResponseToastListener> mUIHttpResponseListenerWeakReference;

        public UIHandler(Looper looper, UIHttpResponseToastListener uiHttpResponseListener) {
            super(looper);
            mUIHttpResponseListenerWeakReference = new WeakReference<UIHttpResponseToastListener>(uiHttpResponseListener);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_TOAST:
                    UIHttpResponseToastListener uiHttpResponseListener = mUIHttpResponseListenerWeakReference.get();
                    if (uiHttpResponseListener != null) {
                        uiHttpResponseListener.onUIHttpResponseToast();
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
    public  void onHttpResponseToast() {
        Message msg = new Message();
        msg.what = SHOW_TOAST;
        mHandler.sendMessage(msg);
    }

    public abstract void onUIHttpResponseToast();
}
