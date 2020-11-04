package com.example.directiongetdata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public TextView textView;
    public Button button;
    public LocationManager locationManager;
    public String commadStr;
    public static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        commadStr = LocationManager.GPS_PROVIDER; //使用GPS定位
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);//LocationManager可以用來獲取當前的位置，追蹤設備的移動路線
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(commadStr, 1000, 0, locationListener);//條件設定為時間間隔1秒，距離改變0米，設定監聽位置變化
                Location location = locationManager.getLastKnownLocation(commadStr);//取得當下的位置資料
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_ACCESS_COARSE_LOCATION);//開啟手機定位權限
                if (location != null)
                    textView.setText("經度:" + location.getLongitude() + "\n緯度:" + location.getLatitude());
                else
                    textView.setText("定位中");
            }
        });
    }
            public LocationListener locationListener=new LocationListener() {//當位置生變化時觸動事件發生
                @Override
                public void onLocationChanged(Location location) {
                    textView.setText("經度:"+location.getLongitude()+"\n緯度:"+location.getLatitude());
                    //當座標改變時觸發此函數，如果Provider傳進相同的座標，它就不會被觸發
                }
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                //Provider的轉態在可用、暫時不可用和無服務三個狀態直接切換時觸發此函數
                }
                @Override
                public void onProviderEnabled(String provider) {
                //Provider被enable時觸發此函數，比如GPS被打開
                }
                @Override
                public void onProviderDisabled(String provider) {
                //Provider被disable時觸發此函數，比如GPS被關閉
                }
            };
        }