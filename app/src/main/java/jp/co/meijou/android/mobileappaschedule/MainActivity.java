package jp.co.meijou.android.mobileappaschedule;

//日付を選択するページ

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.view.View;
import android.util.Log;

import android.widget.CalendarView;

import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import jp.co.meijou.android.mobileappaschedule.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PrefDataStore prefDataStore;

    private double lat;

    private String Year;
    private String Month;
    private String DayOfMonth;
    private String today;

    private int kosuu;

    private int Day;

    /*
    private final ActivityResultLauncher<Intent> getActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                switch (result.getResultCode()){
                    case RESULT_OK:
                        Optional.ofNullable(result.getData())
                                .map(data -> data.getStringExtra("ret"))
                                .map(text -> binding.button1.getText())
                                .ifPresent(text -> binding.todayPlan.setText(text));
                        break;
                    case RESULT_CANCELED:
                        binding.todayPlan.setText("予定：なし");
                        break;
                    default:
                        binding.todayPlan.setText("予定：");
                        break;
                }
            }
    );

     */
    //現在の日付を取得する
    public String getNowDate(){
        final DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        final Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //CalendarViewインスタント生成
        //CalendarView calendar = findViewById(R.id.calendarView);
        binding.textView.setText(getNowDate());

        //日付を選択した時のリスナー
        binding.calendarView.setOnDateChangeListener(
                new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        String message = year + "/" + (month + 1) + "/" + dayOfMonth;
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        Year = String.format("%04d",year);
                        Month = String.format("%02d",month + 1);
                        DayOfMonth = String.format("%02d",dayOfMonth);
                        //ボタンの表示
                        binding.addSchedule.setVisibility(View.VISIBLE);
                        binding.addSchedule.setText(Year + "年" + Month + "月" + DayOfMonth + "日の予定");
                    }
                }
        );

        prefDataStore = PrefDataStore.getInstance(this);

        binding.addSchedule.setOnClickListener(view ->{

            String day = String.format("%02d%02d%02d", Year, Month, DayOfMonth);

            prefDataStore.setString("day", day);//ここが合っているかはActivity２に移さないと分からない
            Log.d("day",day);
            var intent = new Intent(this, MainActivity2.class);
            startActivity(intent);




            //intent.putExtra("text", binding.button1.getText().toString());



            //dataStore.getString("time1").ifPresent(time -> binding.);


        });

    }
}