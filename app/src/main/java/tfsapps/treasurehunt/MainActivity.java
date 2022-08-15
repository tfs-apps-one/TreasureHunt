package tfsapps.treasurehunt;
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
import android.os.Handler;
import android.widget.TextView;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import android.Manifest;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements LocationListener {

    final double IDO_1M = 0.000008983148616;
    final double KEIDO_1M = 0.000010966382364;

    final double IDO_50M = (0.000008983148616 * 50.0f);
    final double KEIDO_50M = (0.000010966382364 * 50.0f);


    LocationManager locationManager;

    //　スレッド処理
    private Timer mainTimer1;					//タイマー用
    private MainTimerTask mainTimerTask1;		//タイマタスククラス
    private Handler mHandler = new Handler();   //UI Threadへのpost用ハンドラ


    private final ActivityResultLauncher<String>
            requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    locationStart();
                } else {
                    Toast toast = Toast.makeText(this,
                            "これ以上なにもできません", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION);
        } else {

            //タイマーインスタンス生成
            this.mainTimer1 = new Timer();
            //タスククラスインスタンス生成
            this.mainTimerTask1 = new MainTimerTask();
            //タイマースケジュール設定＆開始
            this.mainTimer1.schedule(mainTimerTask1, 5000, 10000);
//            locationStart();
        }
    }

    public void locationStart() {
        Log.d("debug", "locationStart()");

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
//        Toast toast = Toast.makeText(this,
//                "経度・緯度　UPDATE！！", Toast.LENGTH_SHORT);
//        toast.show();

        double ido = 0.0f;
        double keido = 0.0f;

        // 緯度の表示
        TextView textView1 = findViewById(R.id.text_view1);
        String str1 = "Latitude:" + location.getLatitude();
        textView1.setText(str1);

        ido = location.getLatitude();

        // 経度の表示
        TextView textView2 = findViewById(R.id.text_view2);
        String str2 = "Longitude:" + location.getLongitude();
        textView2.setText(str2);

        keido = location.getLongitude();

        Toast toast = Toast.makeText(this,
                "UPDATE！！"+"緯度："+ido+"　経度："+keido, Toast.LENGTH_SHORT);
        toast.show();


    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /**
     * タイマータスク派生クラス
     * run()に定周期で処理したい内容を記述
     *
     */
    public class MainTimerTask extends TimerTask {
        @Override
        public void run() {
            //ここに定周期で実行したい処理を記述します
            mHandler.post(new Runnable() {
                public void run() {
                    locationStart();
                }
            });
        }
    }



}