package com.dinghao.qianghongbao;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivityttt";
    private List<AccessibilityNodeInfo> list = new ArrayList<>();
    private List<AccessibilityNodeInfo> lastInfo = new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(findText("微信红包")){
                handler.sendEmptyMessageDelayed(0,5000);
            }else {
                handler.sendEmptyMessageDelayed(0,1000);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showOpenAccessibilityServiceDialog();
        handler.sendEmptyMessage(0);
        startService(new Intent(MainActivity.this, MyAccessibility.class));
    }
    /** 显示未开启辅助服务的对话框*/
    private void showOpenAccessibilityServiceDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_tips_layout, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAccessibilityServiceSettings();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("需要打开辅助服务");
        builder.setView(view);
        builder.setNegativeButton("取消",null);
        builder.setPositiveButton("打开辅助服务", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openAccessibilityServiceSettings();
            }
        });
        builder.show();
    }
    /** 打开辅助服务的设置*/
    private void openAccessibilityServiceSettings() {
        try {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean findText(String name) {
        list.clear();
        MyApplication application = (MyApplication) getApplication();
        AccessibilityNodeInfo info = application.getInfo();
        Log.i(TAG, "findText: "+name);
        if(info!=null){
            recycle(info);
            for (int i = list.size()-1; i >0; i--) {
                if(list.get(i).getText()!=null&&list.get(i).getText().toString().contains(name)){
                    Log.i(TAG, "lookFor: "+list.get(i).getText().toString());
                    AccessibilityNodeInfo targetInfo = list.get(i);
                    if(lastInfo.contains(targetInfo)){
                        continue;
                    }
                    performClick(targetInfo);
                    return true;
                }
            }

        }
        return false;
    }
    public void performClick(AccessibilityNodeInfo nodeInfo) {
        if(nodeInfo == null) {
            return;
        }
        if(nodeInfo.isClickable()) {
            lastInfo.add(nodeInfo);
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        } else {
            performClick(nodeInfo.getParent());
        }
    }
    public void recycle(AccessibilityNodeInfo info) {
        if (info.getChildCount() == 0) {
            Log.i(TAG, "getRootInActiveWindow：" + info.toString());
            list.add(info);
        } else {
            Log.i(TAG, "getRootInActiveWindow：" + info.toString());
            list.add(info);
            for (int i = 0; i < info.getChildCount(); i++) {
                if(info.getChild(i)!=null){
                    recycle(info.getChild(i));
                }
            }
        }
    }
}
