package jp.co.meijou.android.mobileappaschedule;

//特定の日の内容を表示するページ

import androidx.appcompat.app.AppCompatActivity;

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
    }


}