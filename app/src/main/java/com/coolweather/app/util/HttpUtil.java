package com.coolweather.app.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.coolweather.app.MyApplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2015/12/25.
 */
public class HttpUtil {

    /**
     * 发送http请求，使用HttpCallbackListener接口来回调服务器返回的结果
     * @param address 传入的请求地址
     * @param listener 用来回调服务器返回结果
     */
    public static void sendHttpRequest(final String address,final HttpCallbackListener listener){

        if (isNetworkAvailable(MyApplication.getContext())) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    HttpURLConnection connection = null;

                    try {
                        URL url = new URL(address);
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(8000);
                        connection.setReadTimeout(8000);
                        InputStream in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        if (listener != null) {
                            listener.onFinish(response.toString());
                        }
                        in.close();
                    } catch (Exception e) {
                        if (listener != null) {
                            listener.onError(e);
                        }
                    } finally {
                        if (connection != null) {
                            connection.disconnect();
                        }
                    }


                }
            }).start();
        }
        else {
            Toast.makeText(MyApplication.getContext(),"网络不可用，请检查网络连接",Toast.LENGTH_SHORT).show();
        }

    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            //如果仅仅是用来判断网络连接
            //则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
