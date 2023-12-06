package jp.co.meijou.android.mobileappaschedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmActivity extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // アラームが発動したときの処理を記述
        Toast.makeText(context, "時間になりました！！", Toast.LENGTH_SHORT).show();
    }
}