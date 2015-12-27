package com.coolweather.app.util;

/**
 * Created by Administrator on 2015/12/25.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
