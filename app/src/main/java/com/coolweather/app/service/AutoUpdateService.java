package com.coolweather.app.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.coolweather.app.receiver.AutoUpdateReceiver;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.LogUtil;
import com.coolweather.app.util.Utility;

/**
 * 实现一个后台更新天气信息的服务
 */
public class AutoUpdateService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateWeather();
            }
        }).start();
        LogUtil.i("AutoUpdateService", "AutoUpdateService进程运行");
        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        int anHour = 4*60*60*1000;
        long triggerAtTime = SystemClock.elapsedRealtime()+anHour;
        Intent i = new Intent(this, AutoUpdateReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,i,0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pendingIntent);

        return super.onStartCommand(intent, flags, startId);
    }
    private void updateWeather(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String weatherCode = preferences.getString("weather_code","");
        if (!TextUtils.isEmpty(weatherCode)){
            LogUtil.i("weatherCode",weatherCode);
            String address = "http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
            HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    Utility.handleWeatherResponse(AutoUpdateService.this,response);
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            });

        }


    }
}
