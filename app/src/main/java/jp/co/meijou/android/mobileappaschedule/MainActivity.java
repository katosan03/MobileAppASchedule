package jp.co.meijou.android.mobileappaschedule;

//日付を選択するページ

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.widget.CalendarView;

import android.widget.Toast;

import java.util.Calendar;
import java.util.Optional;

import jp.co.meijou.android.mobileappaschedule.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PrefDataStore prefDataStore;

    private double lat;

    private int Year;
    private int Month;
    private int DayOfMonth;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //CalendarViewインスタント生成
        CalendarView calendar = findViewById(R.id.calendarView);

        //日付を選択した時のリスナー
        calendar.setOnDateChangeListener(
                new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        String message = year + "/" + (month + 1) + "/" + dayOfMonth;
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        Year = year;
                        Month = month + 1;
                        DayOfMonth = dayOfMonth;
                        binding.textView.setText(Year + "年" + Month + "月" + DayOfMonth + "日");
                    }
                }
        );

        prefDataStore = PrefDataStore.getInstance(this);

        binding.addSchedule.setOnClickListener(view ->{
            var day = Year + Month + DayOfMonth;
            prefDataStore.setString("day", String.valueOf(day));//ここが合っているかはActivity２に移さないと分からない
            var intent = new Intent(this, MainActivity2.class);
            startActivity(intent);




            //intent.putExtra("text", binding.button1.getText().toString());



            //dataStore.getString("time1").ifPresent(time -> binding.);


        });




    }
}