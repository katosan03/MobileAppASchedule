package jp.co.meijou.android.mobileappaschedule;

//予定を書き足すページ

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;


import android.os.Bundle;

import jp.co.meijou.android.mobileappaschedule.databinding.ActivityMain2Binding;
import jp.co.meijou.android.mobileappaschedule.databinding.ActivityMain3Binding;

public class MainActivity3 extends AppCompatActivity {
    private ActivityMain3Binding binding;
    //private ActivityMain3Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }



}