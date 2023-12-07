package jp.co.meijou.android.mobileappaschedule;

//googleマップの表示，目的地の登録

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.navigation.ui.AppBarConfiguration;

import jp.co.meijou.android.mobileappaschedule.databinding.ActivityMain5Binding;
import java.util.Optional;
public class MainActivity5 extends AppCompatActivity implements OnMapReadyCallback {
    private AppBarConfiguration appBarConfiguration;
    private ActivityMain5Binding binding;

    //初期値名城大学天白キャンパス
    private LatLng mLocation = new LatLng(35.135252, 136.975831);
    private double lat = 0;
    private double log = 0;

    private Marker mMarker = null;
    private PrefDataStore prefDataStore;

    private String underSetting;
    private String day;
    private String kosuu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout file as the content view.
        binding = ActivityMain5Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get a handle to the fragment and register the callback.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    // Get a handle to the GoogleMap object and display marker.
    @Override
    public void onMapReady(GoogleMap googleMap) {

        prefDataStore = PrefDataStore.getInstance(this);

        //入力途中のデータの読み出し
        prefDataStore.getString("day")
                .ifPresent(data -> day = data.toString());
        prefDataStore.getString(day)
                .ifPresent(num -> kosuu = num.toString());
        String name = day + kosuu;
        prefDataStore.getString(name)
                .ifPresent(data -> underSetting = data.toString());


        //現在地の取得
        prefDataStore.getString("lat")
                .ifPresent(latdata -> lat = Double.parseDouble(latdata));

        prefDataStore.getString("log")
                .ifPresent(logdata -> log = Double.parseDouble(logdata));

        if(lat != 0 && log != 0){
            mLocation = new LatLng(lat, log);
        }

        if(googleMap != null){
            //現在地（名城大学）へ移動
            CameraPosition cameraPos = new CameraPosition.Builder()
                    .target(mLocation).zoom(14).build();

            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPos));

            // マーカー準備
            mMarker = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(0, 0)).title("目的地"));

            //位置設定を促すトースト表示
            Toast.makeText(getApplicationContext(), "目的地をタップしてください", Toast.LENGTH_LONG).show();
        }

        // タップ時のイベントハンドラ登録
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                // TODO Auto-generated method stub
                Double deslat,deslog;
                deslat = point.latitude;
                deslog = point.longitude;

                binding.button5.setOnClickListener(view -> {
                    //目的地をdataStoreに格納
                    underSetting = underSetting + "_" + deslat.toString() + "_" + deslog.toString();
                    prefDataStore.setString(name, underSetting);
                    mMarker.setPosition(point);
                    //位置設定を伝えるトースト表示
                    Toast.makeText(getApplicationContext(), "目的地を設定しました", Toast.LENGTH_LONG).show();
                    //MainActivity2（予定表示ページ）に遷移
                    pagePass();
                });
            }
        });
    }

    private void pagePass(){
        //MainActivity2（予定表示）に遷移
        var intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}

/*
参考

GoogleMap表示
https://developers.google.com/maps/documentation/android-sdk/start?hl=ja
事前に立てたピンとマップ上のタッチした場所の直線距離をトースト表示
https://seesaawiki.jp/w/moonlight_aska/d/2%C3%CF%C5%C0%B4%D6%A4%CE%BA%C7%C3%BB%B5%F7%CE%A5%A4%F2%B5%E1%A4%E1%A4%EB
 */