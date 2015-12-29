package com.coolweather.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2015/12/28.
 */
public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}
