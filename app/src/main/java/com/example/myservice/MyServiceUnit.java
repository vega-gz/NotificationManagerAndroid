package com.example.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import java.util.concurrent.TimeUnit;


public class MyServiceUnit extends Service {
    //public static final TimeUnit DAYS;

    public MyServiceUnit() {
    }

    @Override
    public IBinder onBind(Intent intent) { // этот по умолчанию тут был
        Log.d(LOG_TAG, "onBind");
        return null;
    }

    final String LOG_TAG = "myLogs";

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");
        someTask();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }


    void someTask() {

        new Thread(new Runnable() {
            public void run() {
                int temp1 = 1000;
                int i=0;
                if (temp1==1000) {
                    for (int j = 0; i <= temp1; j++) {
                        Log.d(LOG_TAG, "i = " + j);
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                       stopSelf(); //так даем понять сервису что мы закончили
                }

                while (i>=1000){
                    Log.d(LOG_TAG, "i = " + i);
                    ++i;
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stopSelf(); //так даем понять сервису что мы закончили
            }
        }).start();
    }
}
