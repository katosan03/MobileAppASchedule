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
    private PrefDataStore prefDataStore;

    //permissionが許可されているかの確認
    private final ActivityResultLauncher<String>
            requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                    if (isGranted) {
                    locationStart();
                    }
                    //permissionが許可されなかった場合の挙動---------NOTICE----変更必要
                    else {
                        Toast.makeText(getApplicationContext(), "目的地を登録するには位置情報の許可が必要です", Toast.LENGTH_LONG).show();
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
              100, 50, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        //datastoreの準備
        prefDataStore = PrefDataStore.getInstance(this);
        // 緯度の登録
        Double lat = location.getLatitude();
        prefDataStore.setString("lat", lat.toString());

        // 経度の登録
        Double log = location.getLongitude();
        prefDataStore.setString("log", log.toString());

        //MainActivity5(位置設定に遷移)
        var intent = new Intent(this, MainActivity5.class);
        startActivity(intent);

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