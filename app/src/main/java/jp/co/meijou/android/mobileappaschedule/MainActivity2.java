package jp.co.meijou.android.mobileappaschedule;

//特定の日の内容を表示するページ

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import jp.co.meijou.android.mobileappaschedule.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;
    private PrefDataStore prefDataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefDataStore = prefDataStore.getInstance(this);

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