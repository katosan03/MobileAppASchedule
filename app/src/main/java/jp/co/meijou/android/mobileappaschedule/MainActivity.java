package jp.co.meijou.android.mobileappaschedule;

//日付を選択するページ

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.util.Log;

import android.widget.CalendarView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import jp.co.meijou.android.mobileappaschedule.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PrefDataStore prefDataStore;
    private String Year;
    private String Month;
    private String DayOfMonth;
    private String naiyoo = "";
    private int kosuu;
    private String dayKey;

    //現在の日付を取得する(テキスト用)
    public String getNowDate(){
        final DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        final Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }
    //現在の日付を取得する(キー取得用)
    public String getNowDateKey(){
        final DateFormat df = new SimpleDateFormat("yyyyMMdd");
        final Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefDataStore = prefDataStore.getInstance(this);

        //今日の日付を表示する
        binding.todayText.setText(getNowDate());

        dayKey = getNowDateKey();
        //Log.d("dayKey", dayKey);

        prefDataStore.getString(dayKey).ifPresent(kosu -> kosuu = Integer.parseInt(kosu));
        //Log.d("kosu", Integer.toString(kosuu));
        int i = 1;
        if(kosuu != 0){

            while(i <= kosuu && i <= 3){
                String num = Integer.toString(i);
                String daynKey = dayKey + num;
                prefDataStore.getString(daynKey).ifPresent(naiyo -> naiyoo = naiyo);
                Log.d("naiyo", naiyoo);
                String[] datas = naiyoo.split("-");
                String jikoku = datas[0];

                String context;
                //予定が記入されていない場合のエラー回避
                if(datas.length != 1 && datas[1] != null){
                    context = jikoku.substring(0, 2) + "："+ jikoku.substring(2, 4) + "\n" + datas[1];
                }else{
                    context = jikoku.substring(0, 2) + "："+ jikoku.substring(2, 4) + "\n";
                }
                if(i == 1) binding.plan1.setText(context);
                else if(i == 2) binding.plan2.setText(context);
                else binding.plan3.setText(context);
                i++;
            }
        }

        //日付を選択した時のリスナー
        binding.calendarView.setOnDateChangeListener(
                new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        String message = year + "/" + (month + 1) + "/" + dayOfMonth;
                        //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        Year = String.format("%04d",year);
                        Month = String.format("%02d",month + 1);
                        DayOfMonth = String.format("%02d",dayOfMonth);
                        //ボタンの表示
                        binding.addSchedule.setVisibility(View.VISIBLE);
                        binding.addSchedule.setText(Year + "年" + Month + "月" + DayOfMonth + "日の予定");
                    }
                }
        );
        binding.addSchedule.setOnClickListener(view ->{

            String day = Year + Month + DayOfMonth;
            prefDataStore.setString("day", day);//ここが合っているかはActivity２に移さないと分からない
            //Log.d("day",day);
            var intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
        });

    }
}