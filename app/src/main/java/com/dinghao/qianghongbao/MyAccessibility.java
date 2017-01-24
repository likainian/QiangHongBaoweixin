package com.dinghao.qianghongbao;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

@SuppressLint("NewApi")
public class MyAccessibility extends AccessibilityService {
    private static final String TAG = "MyAccessibilityttt";
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG, "handleMessage: 00000000000000");
            AccessibilityNodeInfo info = getRootInActiveWindow();
            if(info!=null){
                MyApplication application = (MyApplication) getApplication();
                application.setInfo(info);
            }
            handler.sendEmptyMessageDelayed(0,1000);
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onServiceConnected() {
        Log.i(TAG, "config success!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.sendEmptyMessage(0);
        return super.onStartCommand(intent, flags, startId);
    }

    @SuppressLint("NewApi")
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onInterrupt() {
        // TODO Auto-generated method stub

    }

}