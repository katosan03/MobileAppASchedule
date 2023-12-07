package jp.co.meijou.android.mobileappaschedule;

//特定の日の内容を表示するページ

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import jp.co.meijou.android.mobileappaschedule.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;
    private PrefDataStore prefDataStore;


    String times[];
    String plans[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefDataStore = prefDataStore.getInstance(this);

        String ymd = String.valueOf(prefDataStore.getString("day"));
        String n = String.valueOf(prefDataStore.getString(ymd)); //予定の個数
        String ymdn = ymd + n;
        String hpdd = String.valueOf(prefDataStore.getString(ymdn)); //hhmm-plan-deslat-deslog

        String[] hpdds = hpdd.split("-"); //hpddをhhmm ,plan ,deslat ,deslogに分ける
        String hhmm = hpdds[0];
        String plan = hpdds[1]; //予定
        int hm = Integer.parseInt(hhmm);
        int hour = hm / 100;
        int minute = hm - hour*100;
        String time = hour + ":" + minute; //時間

        times[Integer.parseInt(n)] = time;
        plans[Integer.parseInt(n)] = plan;

        //すでにデータがあれば記載
        for(int i = 0; i < Integer.parseInt(n); i++) {
            if(i == 0) {
                binding.texttime1.setText(times[0]);
                binding.textnaiyou1.setText(plans[0]);
            }
            if(i == 1) {
                binding.texttime2.setText(times[1]);
                binding.textnaiyou2.setText(plans[1]);
            }
            if(i == 2) {
                binding.texttime3.setText(times[2]);
                binding.textnaiyou3.setText(plans[2]);
            }
        }

        /*
        //すでにデータがあれば記載
        prefDataStore.getString("time1")
                .ifPresent(name -> binding.texttime1.setText(name));

        prefDataStore.getString("naiyou1")
                .ifPresent(name -> binding.textnaiyou1.setText(name));



        //すでにデータがあれば記載
        prefDataStore.getString("time2")
                .ifPresent(name -> binding.texttime1.setText(name));

        prefDataStore.getString("naiyou2")
                .ifPresent(name -> binding.textnaiyou1.setText(name));

         */

        //プラスボタンを押した場合（予定を追加する）
        binding.buttonPlus.setOnClickListener(view ->{
            var intent = new Intent(this, MainActivity3.class);
            startActivity(intent);
        });

        //戻るボタンを押した場合（MainActivityに戻る）
        binding.buttonReturn.setOnClickListener(view ->{
            var intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }



}