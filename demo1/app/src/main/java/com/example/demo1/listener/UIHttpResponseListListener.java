package com.example.demo1.listener;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

public abstract class UIHttpResponseListListener implements HttpResponseListListener {
    private static final int UPDATE_LIST = 1;

    private static class UIHandler extends Handler {
        private final WeakReference<UIHttpResponseListListener> mUIHttpResponseListenerWeakReference;

        public UIHandler(Looper looper, UIHttpResponseListListener uiHttpResponseListener) {
            super(looper);
            mUIHttpResponseListenerWeakReference = new WeakReference<UIHttpResponseListListener>(uiHttpResponseListener);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_LIST:
                    UIHttpResponseListListener uiHttpResponseListener = mUIHttpResponseListenerWeakReference.get();
                    if (uiHttpResponseListener != null) {
                        uiHttpResponseListener.onUIHttpResponseList();
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
    public  void onHttpResponseList() {
        Message msg = new Message();
        msg.what = UPDATE_LIST;
        mHandler.sendMessage(msg);
    }

    public abstract void onUIHttpResponseList();
}
