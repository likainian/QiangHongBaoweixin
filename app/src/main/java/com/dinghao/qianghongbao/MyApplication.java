package com.dinghao.qianghongbao;

import android.app.Application;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by li on 2017/1/20.
 */

public class MyApplication extends Application {
    private AccessibilityNodeInfo info;

    public AccessibilityNodeInfo getInfo() {
        return info;
    }

    public void setInfo(AccessibilityNodeInfo info) {
        this.info = info;
    }
}
