package jp.co.meijou.android.mobileappaschedule;

//予定を書き足すページ

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.app.AlarmManager;
import android.app.PendingIntent;

import java.util.Calendar;

import android.content.Intent;
import android.widget.Toast;
import jp.co.meijou.android.mobileappaschedule.databinding.ActivityMain3Binding;

public class MainActivity3 extends AppCompatActivity {
    private ActivityMain3Binding binding;
    private PrefDataStore prefDataStore;
    private String ymd;
    private String ymdr;
    private String hmpdd;
    private String year;
    private String month;
    private String day;
    private String shour;
    private String sminute;

    private final String[] hour = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}; //時の配列
    private final String[] minute = {"00", "10", "20", "30", "40", "50"}; //分の配列

    private final String CHANNEL_ID = "default";
    private AlarmManager am;

    int plann = 0; //予定の個数

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefDataStore = prefDataStore.getInstance(this);

        prefDataStore.getString("day").ifPresent(datas -> ymd = datas.toString());
        year = ymd.substring(0, 4);
        month = ymd.substring(4, 6);
        day = ymd.substring(6, 8);

        prefDataStore.getString(ymd).ifPresent(plans -> plann = Integer.parseInt(plans));  //今までに登録のあるその日（day）予定の個数

        String ymds = year + "/" + month + "/" + day; //yyyy mm dd の形に再構築
        binding.day.setText(ymds);

        binding.buttonOk.setOnClickListener(view -> {    //決定ボタンをクリックしたらDataStoreに入力された文字を保存
            var intent = new Intent(this, MainActivity2.class);

            int hour = Integer.parseInt(binding.textViewh.getText().toString());
            int minute = Integer.parseInt(binding.textViewm.getText().toString());
            shour = String.format("%02d",hour);
            sminute = String.format("%02d",minute);
            var schedule = binding.editschedule.getText().toString();
            //String time = hour + ":" + minute;
            prefDataStore.getString("day").ifPresent(datas -> ymdr = datas.toString());

            plann += 1;
            String number = Integer.toString(plann);
            String ymdn = ymdr + number;   //yyyymmdd(n)
            hmpdd = shour + sminute + "-" + schedule;
            //datastoreに格納
            prefDataStore.setString(ymd, number);    //hhmm-plan-deslat-deslog
            prefDataStore.setString(ymdn, hmpdd);    //yyyymmdd(n)キー

            //permissionの許可-アラームのセット
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);

            startActivity(intent); //mainに移動
        });

        binding.buttonNg.setOnClickListener(view -> { //戻るボタンの動作
            var intent2 = new Intent(this, MainActivity2.class); //main2に移動
            startActivity(intent2);
        });

        binding.buttonDest.setOnClickListener(view -> { //目的地ボタンの動作
            var intent3 = new Intent(this, MainActivity4.class); //main4へ移動

            int hour = Integer.parseInt(binding.textViewh.getText().toString());
            int minute = Integer.parseInt(binding.textViewm.getText().toString());
            shour = String.format("%02d",hour);
            sminute = String.format("%02d",minute);
            var schedule = binding.editschedule.getText().toString();

            prefDataStore.getString("day").ifPresent(datas -> ymdr = datas.toString());

            plann += 1;
            String number = Integer.toString(plann);
            String ymdn = ymdr + number;   //yyyymmdd(n)
            hmpdd = shour + sminute + "-" + schedule;
            //datastoreに格納
            prefDataStore.setString(ymd, number);    //hhmm-plan-deslat-deslog
            prefDataStore.setString(ymdn, hmpdd);    //yyyymmdd(n)キー

            //permissionの許可-アラームのセット
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);

            startActivity(intent3);
        });

        // ArrayAdapter
        ArrayAdapter<String> adapter  //spinner1,2の設定
                = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, hour);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter2
                = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, minute);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // spinner に adapter をセット
        binding.spinnerh.setAdapter(adapter);
        binding.spinnerm.setAdapter(adapter2);

        // リスナーを登録
        binding.spinnerh.setOnItemSelectedListener(new OnItemSelectedListener() {
            //　アイテムが選択された時
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                String item = (String) spinner.getSelectedItem();
                binding.textViewh.setText(item);
            }

            //　アイテムが選択されなかった
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });
        binding.spinnerm.setOnItemSelectedListener(new OnItemSelectedListener() {
            //　アイテムが選択された時
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                String item = (String) spinner.getSelectedItem();
                binding.textViewm.setText(item);
            }

            //　アイテムが選択されなかった
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch sw1 = findViewById(R.id.switch1); //switch1の設定

        binding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { //PMなら12を足したあたいを表示
                    Integer a = 12;
                    String b = (String) binding.textViewh.getText();
                    int c = Integer.parseInt(b);
                    int d = a + c;
                    Integer i = Integer.valueOf(d);
                    String number = i.toString();

                    binding.textViewh.setText(number);

                } else { //AMの場合
                    Integer a = 12;
                    String b = (String) binding.textViewh.getText();
                    int c = Integer.parseInt(b);
                    if (c > 12) { //時間表記をAMのものに戻す
                        int d = c - a;
                        Integer i = Integer.valueOf(d);
                        String number = i.toString();
                        binding.textViewh.setText(number);
                    } else {
                        binding.textViewh.setText(b);
                    }
                }
            }
        });

    }

    private void setAlarm() { //アラームの設定の中身
        createNotificationChannel();
        String zikoku = (String) binding.textViewh.getText();
        int hour = Integer.parseInt(zikoku);
        String hun = (String) binding.textViewm.getText();
        int minute = Integer.parseInt(hun);

        // アラームを設定するIntentを作成
        Intent intent = new Intent(this, AlarmActivity.class);
        intent.putExtra("message", hmpdd);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        //カレンダーの作成
        Calendar calendar = Calendar.getInstance();     //現在時間が取得される
        //指定時間をセット
        calendar.setTimeInMillis(System.currentTimeMillis());
        //カレンダーにセット
        calendar.set(Calendar.YEAR, Integer.parseInt(year));
        calendar.set(Calendar.MONTH, Integer.parseInt(month));
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0)  ;              // 任意の秒を設定

        // アラームをセットする
        am = (AlarmManager) getSystemService(ALARM_SERVICE);

        if (am != null) {
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            // トーストで設定されたことをを表示
            //Toast.makeText(getApplicationContext(),"アラームを設定しました", Toast.LENGTH_SHORT).show();

            Log.d("debug", "start");
        }
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Schedule";
            String description = hmpdd;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private final ActivityResultLauncher<String>
            requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    setAlarm();
                }
                //permissionが許可されなかった場合の挙動
                else {
                    Toast.makeText(getApplicationContext(), "通知を受けるには設定から許可してください", Toast.LENGTH_LONG).show();
                }
            });
}

/*
任意の時間に通知を発行（kotlin）
https://tech.amefure.com/android-notify-time-specification
通知の時間指定
https://akira-watson.com/android/alarm-notificationmanager.html
notification
https://developer.android.com/training/notify-user/build-notification?hl=ja#java
 */