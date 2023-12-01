package jp.co.meijou.android.mobileappaschedule;

import android.location.Location;

import androidx.annotation.NonNull;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Date;

//時刻，現在地の取得と距離の計算

public class GetPlace {

    public static String getdate(){
        Date date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddkkmm");
        String sDate = sdf.format(date).toString();
        System.out.println(sDate);
        return sDate;
    }

    // 現在地と目的地との最短距離の計算
    public static double getdistance(@NonNull LatLng destination, @NonNull LatLng current){
        float[] distance = new float[1];
        Location.distanceBetween(destination.latitude, destination.longitude, current.latitude, current.longitude, distance);
        double res = Double.parseDouble(((Float)distance[0]).toString());
        return res;
    }

}
/*
他のActivityでGetPlaceする場合
// 位置情報許可要求のダイアログを表示
　　　　　　　if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            String[] permissions = {
                    android.Manifest.permission.ACCESS_FINE_LOCATION,   // 高精度計測
            };

            final int REQ_CODE = 1;
            ActivityCompat.requestPermissions(this, permissions, REQ_CODE);
        }else {
            var intent = new Intent(this, MainActivity4.class);
            startActivity(intent);
            //時刻の近い予定が設定されていないか確認
            prefDataStore.getString("lat").ifPresent(latdata -> lat = Double.parseDouble(latdata));
            if(lat != 0){
                //GetPlace gp = new GetPlace();
                //gp.getdistance();
            }
        }


 */

/*
https://lapture.net/?p=2622
https://www.sejuku.net/blog/21098
https://www.rouge.gr.jp/~fuku/androidstudio/location/
 */