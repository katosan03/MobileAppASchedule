package jp.co.meijou.android.mobileappaschedule;

//日付を選択するページ

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Optional;

import jp.co.meijou.android.mobileappaschedule.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PrefDataStore prefDataStore;


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

        prefDataStore = PrefDataStore.getInstance(this);

        binding.button1.setOnClickListener(view ->{
            var intent = new Intent(this, MainActivity2.class);
            startActivity(intent);





            //intent.putExtra("text", binding.button1.getText().toString());
            var day = binding.button1.getText().toString();
            prefDataStore.setString("day", day);
            /*

            dataStore.getString("time1")
                    .ifPresent(time -> binding.);
             */

        });




    }
}