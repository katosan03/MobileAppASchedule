package jp.co.meijou.android.mobileappaschedule;

//位置情報を扱う（練習）

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.TextView;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import android.Manifest;
import jp.co.meijou.android.mobileappaschedule.databinding.ActivityMain4Binding;

public class MainActivity4 extends AppCompatActivity implements LocationListener{
    LocationManager locationManager;
    private ActivityMain4Binding binding;

    //permissionが許可されているかの確認
    private final ActivityResultLauncher<String>
            requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                    if (isGranted) {
                    locationStart();
                    }
                    else {
                    Toast toast = Toast.makeText(this,
                    "これ以上なにもできません", Toast.LENGTH_SHORT);
                    toast.show();
                    }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION);
        }
        else{
        locationStart();
        }

        //ボタンを押した場合（目的地を追加する）
        binding.button.setOnClickListener(view ->{
            var intent = new Intent(this, MainActivity5.class);
            startActivity(intent);

        });
    }

    private void locationStart(){
        Log.d("debug","locationStart()");

        // LocationManager インスタンス生成
        locationManager =
                (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager != null && locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER)) {
            Log.d("debug", "location manager Enabled");
        } else {
            // GPSを設定するように促す
            Intent settingsIntent =
                    new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
            Log.d("debug", "not gpsEnable, startActivity");
        }

        if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);

            Log.d("debug", "checkSelfPermission false");
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000, 50, this);

    }

    @Override
    public void onLocationChanged(Location location) {
        // 緯度の表示
        TextView textView1 = findViewById(R.id.textViewLoca);
        String str1 = "Latitude:"+location.getLatitude();
        textView1.setText(str1);

        // 経度の表示
        TextView textView2 = findViewById(R.id.textViewLoca2);
        String str2 = "Longitude:"+location.getLongitude();
        textView2.setText(str2);
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

/*
参考

位置情報を取得する
https://akira-watson.com/android/gps.html
GoogleMapのAPI使うための設定
https://developers.google.com/maps/documentation/android-sdk/start?hl=ja
 */