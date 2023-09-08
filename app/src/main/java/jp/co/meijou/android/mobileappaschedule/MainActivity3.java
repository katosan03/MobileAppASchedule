package jp.co.meijou.android.mobileappaschedule;

//予定を書き足すページ

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;


import android.content.Intent;
import android.os.Bundle;

import java.util.Optional;

import jp.co.meijou.android.mobileappaschedule.databinding.ActivityMain2Binding;
import jp.co.meijou.android.mobileappaschedule.databinding.ActivityMain3Binding;

public class MainActivity3 extends AppCompatActivity {

    private ActivityMain3Binding binding;
    private PrefDataStore dataStore;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataStore.getString("day1")
                .ifPresent(day -> binding.day.setText(day));


        //Optional.ofNullable(getIntent().getStringExtra("day"))
          //      .ifPresent(day -> binding.day.setText(day));

        dataStore = PrefDataStore.getInstance(this); //Contextの準備が出来る onCreate() で prefDataStore を初期化


        binding.buttonOk.setOnClickListener(view -> { //決定ボタンをクリックしたらDataStoreに入力された文字を保存
            var intent = new Intent(this, MainActivity.class);
            //var intent2 = new Intent(this, MainActivity2.class);
            //intent.putExtra("time", binding.edittime.getText().toString());
            //intent.putExtra("schedule", binding.editschedule.getText().toString());
            //intent2.putExtra("time", binding.edittime.getText().toString());
            //intent2.putExtra("schedule", binding.editschedule.getText().toString());

            var time = binding.edittime.getText().toString();
            var schedule = binding.edittime.getText().toString();
            dataStore.setString("time1", time);
            dataStore.setString("naiyou1", schedule);
            startActivity(intent);
        });


        binding.buttonNg.setOnClickListener(view -> {
            var intent2 = new Intent(this, MainActivity2.class);
            startActivity(intent2);

        });

    }


}