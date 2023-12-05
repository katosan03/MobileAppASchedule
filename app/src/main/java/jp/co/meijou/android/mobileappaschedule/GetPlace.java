package jp.co.meijou.android.mobileappaschedule;

//時刻，現在地の取得と距離の計算（hanteiの結果として　0:時刻の近い予定なしor位置情報OFF，1:すでに近くにいる，2:遠くにいる）

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.datastore.core.DataStore;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReferenceArray;


public class GetPlace {

    private PrefDataStore prefDataStore;
    private int numPlan = 0;
    private Double lat = 0.0;
    private Double log = 0.0;
    private LatLng nowL;

    GetPlace(PrefDataStore prefDataStore){
        this.prefDataStore = prefDataStore;
        prefDataStore.getString("lat").ifPresent(str -> lat = Double.parseDouble(str));
        prefDataStore.getString("log").ifPresent(str -> log = Double.parseDouble(str));
        nowL = new LatLng(lat,log);
    }


    //日付の取得
    public String getdate(){
        Date date = new Date();

        SimpleDateFormat sdate = new SimpleDateFormat("yyyyMMddkkmm");
        String sDate = sdate.format(date).toString();
        return sDate;
    }

    //時間の取得
    public String gettime(){
        Date date = new Date();

        SimpleDateFormat time = new SimpleDateFormat("kkmm");
        String sTime = time.format(date).toString();
        return sTime;
    }

    // 現在地と目的地との最短距離の計算
    public double getdistance(@NonNull LatLng destination, @NonNull LatLng current){
        float[] distance = new float[1];
        Location.distanceBetween(destination.latitude, destination.longitude, current.latitude, current.longitude, distance);
        double res = Double.parseDouble(((Float)distance[0]).toString());
        return res;
    }

    //その日の予定の取得
    public int hantei(){
        String today = getdate();
        prefDataStore.getString(today)
                .ifPresent(num -> numPlan = Integer.parseInt(num));
        if(numPlan == 0 || (lat == 0.0 && log == 0.0)){
            return 0;
        }
        else{
            int i =0;
            while (i < numPlan){
                //その日の予定の検索
                Integer a = Integer.valueOf(i);
                String stri = a.toString();
                String keyname = today + stri;

                String str1 = "0";
                String[] arr = new String[2];
                prefDataStore.getString(keyname).ifPresent(str -> arr[0] = str);
                String[] naiyou = arr[0].split("_");
                //設定時間
                int settime = Integer.parseInt(naiyou[0]);
                //現在時間
                int time = Integer.parseInt(gettime());

                //設定時間が現在時間+-5分の場合
                if(settime == time){
                    Double deslat = 0.0;
                    Double deslog = 0.0;

                    deslat = Double.parseDouble(naiyou[2]);
                    deslog = Double.parseDouble(naiyou[3]);

                    LatLng desL = new LatLng(deslat,deslog);

                    //距離の取得，目的地から1km以内なら1，それよりも遠い場合は2を返す
                    if(getdistance(nowL, desL) <= 1000){
                        return 1;
                    }else{
                        return 2;
                    }

                }
                i++;
            }
        }
        return 0;

    }
}
/*
NOTE---他のActivityでGetPlaceする場合

        //位置情報の確認
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION);
        }
        else{
            GetPlace gp = new GetPlace();
            int flag = gp.hantei();
            if(flag == 0){}
            //予定目的地付近にいる場合
            else if(flag == 1){
                Toast.makeText(getApplicationContext(), "目的地付近にいます", Toast.LENGTH_LONG).show();
            }
            //予定目的地の遠くにいる場合
            else{
                Toast.makeText(getApplicationContext(), "目的地付近にいないようです。急げばきっと間に合いますよ", Toast.LENGTH_LONG).show();
            }
        }

 */

/*
位置情報の扱い（2）
https://lapture.net/?p=2622
Date型の使い方
https://www.sejuku.net/blog/21098
 */