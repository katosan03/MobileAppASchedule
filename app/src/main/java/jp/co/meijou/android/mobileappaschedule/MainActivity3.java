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
    private PrefDataStore dataStore;

    private final String[] hour = {"1","2","3","4","5","6","7","8","9","10","11","12"};

    private final String[] minute = {"00","10","20","30","40","50"};

    private TextView textViewh;
    private TextView textViewm;
    private TextView schedule;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataStore = PrefDataStore.getInstance(this); //Contextの準備が出来る onCreate() で prefDataStore を初期化.
        dataStore.getString("day")
                .ifPresent(day -> binding.day.setText(day));

        textViewh = findViewById(R.id.textViewh);
        textViewm = findViewById(R.id.textViewm);
        schedule = findViewById(R.id.editschedule);

        binding.buttonOk.setOnClickListener(view -> { //決定ボタンをクリックしたらDataStoreに入力された文字を保存
            var intent = new Intent(this, MainActivity.class);


            var hour = binding.textViewh.getText().toString();
            var minute = binding.textViewm.getText().toString();
            var schedule = binding.editschedule.getText().toString();
            String time = hour + minute;
            dataStore.setString("time1", time);
            dataStore.setString("naiyou1", schedule);
            startActivity(intent);
            //setAlarm();
        });


        binding.buttonNg.setOnClickListener(view -> {
            var intent2 = new Intent(this, MainActivity2.class);
            startActivity(intent2);

        });

        binding.buttonDest.setOnClickListener(view -> {
            var intent3 = new Intent(this, MainActivity4.class);
            startActivity(intent3);

        });

        setContentView(R.layout.activity_main);

        Spinner spinnerh = findViewById(R.id.spinnerh);
        Spinner spinnerm = findViewById(R.id.spinnerm);

        // ArrayAdapter
        ArrayAdapter<String> adapter
                = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, hour);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter2
                = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, minute);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // spinner に adapter をセット
        spinnerh.setAdapter(adapter);
        spinnerm.setAdapter(adapter2);

        // リスナーを登録
        spinnerh.setOnItemSelectedListener(new OnItemSelectedListener() {
            //　アイテムが選択された時
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                Spinner spinner = (Spinner)parent;
                String item = (String)spinner.getSelectedItem();
                textViewh.setText(item);
            }
            //　アイテムが選択されなかった
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });
        spinnerm.setOnItemSelectedListener(new OnItemSelectedListener() {
            //　アイテムが選択された時
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                Spinner spinner = (Spinner)parent;
                String item = (String)spinner.getSelectedItem();
                textViewm.setText(item);
            }
            //　アイテムが選択されなかった
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch sw1 = findViewById(R.id.switch1);


        sw1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Integer a = 12;
                String b = (String) textViewh.getText();
                int c = Integer.parseInt(b);
                int d = a + c;
                Integer i = Integer.valueOf(d);
                String number = i.toString();

                textViewh.setText(number);

            } else {
                Integer a = 12;
                String b = (String) textViewh.getText();
                int c = Integer.parseInt(b);
                if(c > 12) {
                    int d = c - a;
                    Integer i = Integer.valueOf(d);
                    String number = i.toString();
                    textViewh.setText(number);
                }
                else{
                    textViewh.setText(b);
                }
            }
        });

    }

    private void setAlarm(){
        Calendar calendar = Calendar.getInstance();
        String zikoku = (String) textViewh.getText();

        int hour = Integer.parseInt(zikoku);
        String hun = (String) textViewm.getText();

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