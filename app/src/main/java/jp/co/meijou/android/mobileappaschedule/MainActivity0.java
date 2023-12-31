package jp.co.meijou.android.mobileappaschedule;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import jp.co.meijou.android.mobileappaschedule.databinding.ActivityMain0Binding;

public class MainActivity0 extends AppCompatActivity implements LocationListener {
    LocationManager locationManager;
    Timer timer;
    private ActivityMain0Binding binding;
    private PrefDataStore prefDataStore;

    //アプリを立ち上げた直後の場合：0，その他：1
    public int flag = 0;

    //permissionが許可されているかの確認
    private final ActivityResultLauncher<String>
            requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    locationStart();
                    //MainActivityに遷移
                    pagePass();
                }
                //permissionが許可されなかった場合の挙動
                else {
                    Toast.makeText(getApplicationContext(), "目的地を設定するには位置情報を許可してください", Toast.LENGTH_LONG).show();
                    //MainActivityに遷移
                    pagePass();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain0Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //datastoreの準備
        prefDataStore = PrefDataStore.getInstance(this);

        //位置情報の取得スタート
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissionLauncher.launch(
                    android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        else{
            locationStart();
            pagePass();
        }
    }

    private void locationStart(){
        Log.d("debug","locationStart()");

        // LocationManager インスタンス生成
        locationManager =
                (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager != null && locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER)) {
            //Log.d("debug", "location manager Enabled");
        } else {
            // GPSを設定するように促す
            Intent settingsIntent =
                    new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
            //Log.d("debug", "not gpsEnable, startActivity");
        }

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);

            Log.d("debug", "checkSelfPermission false");

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                100, 100, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        // 緯度の登録
        Double lat = location.getLatitude();
        prefDataStore.setString("lat", lat.toString());
        // 経度の登録
        Double log = location.getLongitude();
        prefDataStore.setString("log", log.toString());
    }

    @Override
    public void onProviderEnabled(String provider) {
    }
    @Override
    public void onProviderDisabled(String provider) {
    }

    private void pagePass(){
        //MainActivityに遷移
        var intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}