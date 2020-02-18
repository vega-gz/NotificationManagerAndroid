package com.example.myservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

// передача данных в сервис
// https://startandroid.ru/ru/uroki/vse-uroki-spiskom/158-urok-93-service-peredacha-dannyh-v-servis-metody-ostanovki-servisa.html

public class MainActivity extends AppCompatActivity {
    final String LOG_TAG = "myLogs";
    // Идентификатор уведомления
    private static final int NOTIFY_ID = 101;
    // Идентификатор канала
    private static String CHANNEL_ID1 = "Cat channel";
    // Идентификатор группы
    private static String GROUP_ID = "Cat Group";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    // -- запуск сервиса
    public void onClickStart(View v) {
        startService(new Intent(this, MyServiceUnit.class));
    }

    public void onClickStop(View v)
    {
        stopService(new Intent(this, MyServiceUnit.class));
    }

    // -- запуск сервиса второго с разными параметрами в процессах
    public void onClickStart_Service2(View v) {
        startService(new Intent(this, MyServiceUnit2.class).putExtra("time", 7));
        startService(new Intent(this, MyServiceUnit2.class).putExtra("time", 2));
        startService(new Intent(this, MyServiceUnit2.class).putExtra("time", 4));
    }

    // -- Тестовая кнопка для уведомлений
    public void onClickNotify(View v) {

        //sendNotif();  // метод запуска уведомления
        sendNotif2();  // метод запуска уведомления
        }

        void sendNotif2(){
            NotificationManager notificationManager_2 = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Так проверка на версию каналов или библиотек совместимости это проходит
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID1, "My channel 665", NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.enableLights(true);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationChannel.setDescription("My channel description");
                notificationChannel.setSound(null, null);
                notificationChannel.setLightColor(Color.RED);
                notificationManager_2.createNotificationChannel(notificationChannel);
            }


            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this, CHANNEL_ID1) // Что то в  CHANNEL_ID так как не всплывают те которые выше
                            .setContentTitle("Test Title")
                            .setContentText("Test Message")
                            .setSmallIcon(R.mipmap.ic_launcher);

            notificationManager_2.notify(1, builder.build());

        }

    void sendNotif() {

        //Создадим новые объекты Intent и PendingIntent, которые описывают намерения и целевые действия
        Intent notificationIntent = new Intent(MainActivity.this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(MainActivity.this);

        // каналы не работают
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Так проверка на версию каналов или библиотек совместимости это проходит
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID1, "My channel",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("My channel description");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(false);
            channel.setSound(null, null);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID1)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Title")
                        .setContentText("Notification text")
                        //.setPriority(NotificationCompat.PRIORITY_DEFAULT) // это из примера не работатет
                        .setContentIntent(contentIntent) // присоеденить действие
                        // необязательные настройки
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                                R.drawable.ic_launcher_foreground)) // какая то картинка
                        .setTicker("Последнее китайское предупреждение!") // до Lollipop
                        .setAutoCancel(true); // автоматически закрыть уведомление после нажатия

        notificationManager.notify(NOTIFY_ID, builder.build());


        /*
        // еще один код для уведомлений советуют его
        Notification notification = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            notification = new Notification();
            notification.icon = R.mipmap.ic_launcher;

        } else {
            // Use new API
            Notification.Builder builder2 = new Notification.Builder() // но тут что то надо ему
                    .setContentIntent(contentIntent) // присоеденить действие
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Notification test1")
                    .enableLights(true) // тут видит эти переменные
            .setContentIntent(contentIntent)
            .setLightColor(Color.RED)
            .enableVibration(false);
            notification = builder.build();
        }
        */

    }

}
