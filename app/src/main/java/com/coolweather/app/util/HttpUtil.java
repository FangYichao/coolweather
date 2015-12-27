package com.coolweather.app.util;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;

                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                            response.append(line);
                            }
                    if (listener != null){
                        listener.onFinish(response.toString());
                    }


                } catch (Exception e) {
                    if (listener != null){
                        listener.onError(e);
                    }
                }
                finally {
                    if(connection != null){
                        connection.disconnect();
                    }
                }


            }
        }).start();


    }

}
