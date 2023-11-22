package jp.co.meijou.android.mobileappaschedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

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

public class MainActivity5 extends AppCompatActivity implements OnMapReadyCallback {
    private AppBarConfiguration appBarConfiguration;
    private ActivityMain5Binding binding;
    private LatLng mKansai = new LatLng(34.435912, 135.243496);
    private Marker mMarker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout file as the content view.
        binding = ActivityMain5Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get a handle to the fragment and register the callback.
        SupportMapFragment mapfragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapfragment.getMapAsync(this);


    }

    // Get a handle to the GoogleMap object and display marker.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(mKansai)
                .title("現在地"));

        if(googleMap != null){
            // 関西国際空港へ移動
            CameraPosition cameraPos = new CameraPosition.Builder()
                    .target(mKansai).zoom(10).build();

            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPos));

            // マーカー準備
            mMarker = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(0, 0)).title("目的地"));

        }

        // タップ時のイベントハンドラ登録
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                // TODO Auto-generated method stub
                // タッチ地点と目的地との最短距離の計算
                float[] results = new float[1];
                Location.distanceBetween(point.latitude, point.longitude, mKansai.latitude, mKansai.longitude, results);
                Toast.makeText(getApplicationContext(), "関空までの距離：" + ( (Float)(results[0]/1000) ).toString() + "Km", Toast.LENGTH_LONG).show();
                mMarker.setPosition(point);
            }
        });
    }
}

/*
参考

GoogleMap表示
https://developers.google.com/maps/documentation/android-sdk/start?hl=ja
事前に立てたピンとマップ上のタッチした場所の直線距離をトースト表示
https://seesaawiki.jp/w/moonlight_aska/d/2%C3%CF%C5%C0%B4%D6%A4%CE%BA%C7%C3%BB%B5%F7%CE%A5%A4%F2%B5%E1%A4%E1%A4%EB
 */