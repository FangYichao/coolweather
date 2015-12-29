package com.coolweather.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.coolweather.app.service.AutoUpdateService;
import com.coolweather.app.util.LogUtil;

/**
 * Created by Administrator on 2015/12/29.
 */
public class AutoUpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        LogUtil.i("AutoUpdateReceiver", "AutoUpdateReceiver收到来自AutoUpdateService的广播");
        Intent intent1 = new Intent(context, AutoUpdateService.class);
        context.startService(intent1);
    }
}
