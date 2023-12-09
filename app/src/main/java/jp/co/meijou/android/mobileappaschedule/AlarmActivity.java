package jp.co.meijou.android.mobileappaschedule;

import static android.icu.number.NumberRangeFormatter.with;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmActivity extends BroadcastReceiver {
    private String receivedData;
    private String channelId = "default";
    private int NOTIFY_ID = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        // アラームが発動したときの処理を記述
        //Toast.makeText(context, "時間になりました！！", Toast.LENGTH_SHORT).show();

        // ブロードキャストを受け取る
        receivedData = intent.getStringExtra("message");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Schedule")
                .setContentText(receivedData)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat
                = NotificationManagerCompat.from(context);

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManagerCompat.notify(NOTIFY_ID, builder.build());
    }
}

/*
PendingIntent
https://developer.android.com/topic/security/risks/pending-intent?hl=ja#java_1
notificationManager
https://developer.android.com/training/notify-user/build-notification?hl=ja#java
 */