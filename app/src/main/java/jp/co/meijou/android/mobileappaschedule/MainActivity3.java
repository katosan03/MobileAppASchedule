package jp.co.meijou.android.mobileappaschedule;

//予定を書き足すページ

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Switch;
import android.app.AlarmManager;
import android.app.PendingIntent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

import android.content.Intent;

import java.util.Optional;

import jp.co.meijou.android.mobileappaschedule.databinding.ActivityMain2Binding;
import jp.co.meijou.android.mobileappaschedule.databinding.ActivityMain3Binding;

public class MainActivity3 extends AppCompatActivity {

    private ActivityMain3Binding binding;
    private PrefDataStore prefDataStore;

    private final String[] hour = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}; //時の配列

    private final String[] minute = {"00", "10", "20", "30", "40", "50"}; //分の配列

    //private TextView textViewh;
    //private TextView textViewm;
    //private TextView schedule;

    int plann = 0; //予定の個数

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefDataStore = prefDataStore.getInstance(this);

        /*
        prefDataStore.getString("day")
                .ifPresent(day -> binding.day.setText(day));
                String Days = binding.day.getText() + "日";
                binding.day.setText(Days);

         */

        String ymd = String.valueOf(prefDataStore.getString("day")); //yyyymmddを分解
        int year = Integer.parseInt(ymd.substring(0, 4));
        int month = Integer.parseInt(ymd.substring(4, 6));
        int day = Integer.parseInt(ymd.substring(6, 8));

        String years = String.valueOf(year);
        String months = String.valueOf(month);
        String days = String.valueOf(day);
        String ymds = years + " " + months + "/" + days; //yyyy mm dd の形に再構築
        binding.day.setText(ymds);

        //textViewh = findViewById(R.id.text_view);
        //textViewm = findViewById(R.id.pratext);
        //schedule = findViewById(R.id.editschedule);

        binding.buttonOk.setOnClickListener(view -> { //決定ボタンをクリックしたらDataStoreに入力された文字を保存
            var intent = new Intent(this, MainActivity.class);

            var hour = binding.textViewh.getText().toString();
            var minute = binding.textViewm.getText().toString();
            var schedule = binding.editschedule.getText().toString();
            String time = hour + ":" + minute;
            prefDataStore.setString("time1", time);
            prefDataStore.setString("naiyou1", schedule);

            setAlarm(); //アラームのセット

            String ymdr = String.valueOf(prefDataStore.getString("day"));
            String deslat = String.valueOf(prefDataStore.getString("lat"));
            String deslog = String.valueOf(prefDataStore.getString("log"));

            String number = String.valueOf(plann);
            String ymdn = ymdr + number; //yyyymmdd(n)
            String hmpdd = hour + minute + "-" + schedule + "-" + deslat +"-" + deslog; //hhmm-plan-deslat-deslog
            prefDataStore.setString(ymdr, String.valueOf(plann)); //yyyymmddキー
            prefDataStore.setString(ymdn, String.valueOf(hmpdd));//yyyymmdd(n)キー

            plann += 1;

            startActivity(intent); //mainに移動
        });



        binding.buttonNg.setOnClickListener(view -> { //戻るボタンの動作
            var intent2 = new Intent(this, MainActivity2.class); //main2に移動

            startActivity(intent2);

        });

        binding.buttonDest.setOnClickListener(view -> { //目的地ボタンの動作
            var intent3 = new Intent(this, MainActivity4.class); //main4へ移動

            plann += 1;
            startActivity(intent3);
        });

        //setContentView(R.layout.activity_main);

        //Spinner spinnerh = findViewById(R.id.spinnerh);
        //Spinner spinnerm = findViewById(R.id.spinnerm);

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


        //sw1.setOnCheckedChangeListener((buttonView, isChecked) -> {
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


    private void setAlarm(){ //アラームの設定の中身
        Calendar calendar = Calendar.getInstance();
        String zikoku = (String) binding.textViewh.getText();

        int hour = Integer.parseInt(zikoku);
        String hun = (String) binding.textViewm.getText();

        int minute = Integer.parseInt(hun);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // アラームを設定するIntentを作成
        Intent intent = new Intent(this, AlarmActivity.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        // アラームを設定
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }


}