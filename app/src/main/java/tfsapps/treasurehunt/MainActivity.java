package tfsapps.treasurehunt;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import android.Manifest;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements LocationListener, MyMap.Callback {

    LocationManager locationManager;
    static private MyMap myMap = null;
//    private final Random rand = new Random(System.currentTimeMillis());

    //　スレッド処理
    private Timer mainTimer1;					//タイマー用
    private MainTimerTask mainTimerTask1;		//タイマタスククラス
    private Handler mHandler = new Handler();   //UI Threadへのpost用ハンドラ

    private double ini_ido = 0.0f;         //今回の位置
    private double ini_keido = 0.0f;       //今回の位置
    private double now_ido = 0.0f;         //今回の位置
    private double now_keido = 0.0f;       //今回の位置
    private double bak1_ido = 0.0f;        //前回の位置
    private double bak1_keido = 0.0f;      //前回の位置

    private LinearLayout lay4;

    //  DB関連
    public MyOpenHelper helper;             //DBアクセス
    private int db_isopen = 0;              //DB使用したか
    private int db_level = 0;               //DB
    private int db_stamina = 0;             //DB
    private int db_money = 0;               //DB
    private int db_coin = 0;                //DB
    private int db_gold = 0;                //DB
    private int db_silver = 0;              //DB
    private int db_bronze = 0;              //DB
    private int db_stage = 0;               //DB
    private int db_scoop = 0;               //DB
    private int db_goggles = 0;             //DB
    private int db_ore_1 = 0;               //DB
    private int db_ore_2= 0;                //DB
    private int db_ore_3 = 0;               //DB
    private int db_item_1 = 0;              //DB
    private int db_item_2 = 0;              //DB
    private int db_item_3 = 0;              //DB
    private int db_syssw_1 = 0;             //DB
    private int db_syssw_2 = 0;             //DB
    private int db_syssw_3 = 0;             //DB
    private int db_syssw_4 = 0;             //DB
    private int db_syssw_5 = 0;             //DB

    private boolean get_GPS = false;

    private MyDialog myDialog;

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
/*
            //タイマーインスタンス生成
            this.mainTimer1 = new Timer();
            //タスククラスインスタンス生成
            this.mainTimerTask1 = new MainTimerTask();
            //タイマースケジュール設定＆開始
            this.mainTimer1.schedule(mainTimerTask1, 5000, 10000);

 */
//            locationStart();
        }
    }
    /**
     * OS関連処理
     *
     */
    @Override
    public void onStart() {
        super.onStart();
        //DBのロード
        /* データベース */
        helper = new MyOpenHelper(this);
        AppDBInitRoad();
    }
    @Override
    public void onResume() {
        super.onResume();
        //動画
    }
    @Override
    public void onPause(){
        super.onPause();
        //  DB更新
        AppDBUpdated();
    }
    @Override
    public void onStop(){
        super.onStop();
        //  DB更新
        AppDBUpdated();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        //  DB更新
        AppDBUpdated();
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

        /*
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                5000, 5, this);

        */

        /*
            GPS_PROVIDER	    GPSを利用した比較的精度の高い位置情報を使う
            NETWORK_PROVIDER    ネットワークを利用した位置情報を使う
            PASSIVE_PROVIDER    直近で使用された位置情報を使う

            第１〜４までの引数
                Providerの種類
                通知の最小時間の間隔
                通知の最小距離の間隔
                LocationListenerのインスタンス
         */
        List<String> providers = locationManager.getProviders(true);
        /* 並行して、３のプロバイダーを起動する */
        for (String provider : providers){
            locationManager.requestLocationUpdates(provider,
                    1000, 3, this);
        }
        get_GPS = true;
    }

    @Override
    public void onLocationChanged(Location location) {

        double ido = 0.0f;
        double keido = 0.0f;

        if (this.mainTimer1 == null){
            return;
        }

        if (get_GPS == true){
            get_GPS = false;
        }
        else{
            /* 取得ずみのためスキップ */
            return;
        }

        ido = location.getLatitude();
        keido = location.getLongitude();

        Toast toast = Toast.makeText(this,
                "UPDATE！！\n"+"緯度："+ido+"　経度："+keido, Toast.LENGTH_SHORT);
        toast.show();

        bak1_ido = now_ido;
        bak1_keido = now_keido;
        now_ido = location.getLatitude();
        now_keido = location.getLongitude();

        if (ini_ido == 0.0f || ini_keido == 0.0f){
            ini_ido = now_ido;
            ini_keido = now_keido;
            myMap.InitialSetting(ini_ido, ini_keido);
        }
        else{
            myMap.UpdatePosition(now_ido, now_keido);
        }
//        setContentView(R.layout.activity_sub);
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /************************************************
        ゲームスタート
     ************************************************/
    public void onGameScreen(View v){
        //サブ画面へ移動
        setContentView(R.layout.activity_sub);
        SubShow();
//        lay4.addView(myMap, new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));


        /* スタートボタン押下後でよいのか？ */
/*        if (myMap == null) {
            myMap = findViewById(R.id.my_map);
        }
 */

    }
    /************************************************
        画面表示（サブ画面＝ドラゴンレーダー画面）
     ************************************************/
    public void SubShow(){
        ImageButton imgBtn = findViewById(R.id.btn_img_scoop);
        TextView txtstatus = findViewById(R.id.text_status);
        Button btn = findViewById(R.id.btn_start_end);
        if (this.mainTimer1 == null) {
            txtstatus.setText("「START」を押して宝探しを開始して下さい");
            btn.setText("START");
        }
        else{
            String tmp =
                    "\n赤：はじめの位置　青：いまの位置　" +
                    "\n黄：お宝？の場所　緑：穴掘り開始　" +
                    "\n\n";


            if (myMap.isHitTreasure()) {
                tmp += "「スコップ」ボタンで穴掘り開始\n";
            } else {
                tmp += "お宝？の場所まで移動しよう・・・\n";
            }
            btn.setText("END");
            txtstatus.setText(tmp);
            SubStamina();
        }
    }
    /************************************************
        スタミナゲージ
     ************************************************/
    public void SubStamina() {
        ProgressBar bar = findViewById(R.id.progress_stamina);
        bar.setMax(100);
        bar.setMin(0);
        bar.setProgress(db_stamina);

        TextView txt = findViewById(R.id.text_stamina);
        txt.setText("スタミナ残:" + db_stamina + "%");
    }
    /************************************************
         スタート／エンド　ボタン
     ************************************************/
    public void onSubStartEnd(View v) {
        //スタート
        if (this.mainTimer1 == null) {
            LinearLayout lay4 = (LinearLayout)findViewById(R.id.linearLayout4);
            myMap = new MyMap(this);
            myMap.setCallback(this);
            lay4.addView(myMap, new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));

            //タイマーインスタンス生成
            this.mainTimer1 = new Timer();
            //タスククラスインスタンス生成
            this.mainTimerTask1 = new MainTimerTask();
            //タイマースケジュール設定＆開始
            //      this.mainTimer1.schedule(mainTimerTask1, 5000, 10000);
            this.mainTimer1.schedule(mainTimerTask1, 1000, 5000);
        }
        //エンド
        else {
            setContentView(R.layout.activity_sub);
            gameClear();
        }
        SubShow();
    }
    /************************************************
         宝探しゲーム　終了処理
     ************************************************/
    public void gameClear() {
        if (this.mainTimer1 != null) {
            this.mainTimer1.cancel();
            this.mainTimer1 = null;
        }
        if (myMap != null){
            myMap = null;
        }
        ini_ido = 0.0f;         //今回の位置
        ini_keido = 0.0f;       //今回の位置
        now_ido = 0.0f;         //今回の位置
        now_keido = 0.0f;       //今回の位置
        bak1_ido = 0.0f;        //前回の位置
        bak1_keido = 0.0f;      //前回の位置
    }
    /************************************************
         サブ画面のメニュー　ボタン
     ************************************************/
    public void onSubMenu(View v) {
        gameClear();
        setContentView(R.layout.activity_main);
    }
    /************************************************
        スコップ　ボタン（宝ザクザク）
     ************************************************/
    public void onSubScoop(View v){
        MyDialog pop = new MyDialog(this, "穴掘り", "お宝ザクザク", 1);

        if (myMap.isHitTreasure()){
            pop.PopShow();
//           ScoopResult();
        }
        else{
        }

    }
    //結果
    public void ScoopResult(){
        int type = myMap.ZakuZakuResult();
        MyDialog pop;
        //スタミナ減少
        db_stamina -= 10;
        if (db_stamina < 0){
            db_stamina = 0;
        }

        switch (type){
            case 0:
                pop = new MyDialog(this, "レア宝", "やったー！！", 0);
                pop.PopShow();
                break;
            case 1:
                pop = new MyDialog(this, "普通宝", "普通だね！！", 0);
                pop.PopShow();
                break;
            case 2:
                pop = new MyDialog(this, "ガラクタ", "残念１ー！！", 0);
                pop.PopShow();
                break;
            case 3:
                pop = new MyDialog(this, "ガラクタ２", "残念２ー！！", 0);
                pop.PopShow();
                break;

            default:
                break;
        }
    }



    /************************************************
        買い物
     ************************************************/
    public void onShop(View v){

    }
    /************************************************
        ステータス（戦歴）
     ************************************************/
    public void onStatus(View v){

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
                    SubShow();
                }
            });
        }
    }


    /***************************************************
     　↓↓↓ 以下、DB関連処理　↓↓↓
     ***************************************************/

    /***************************************************
        DB初期ロードおよび設定
    ****************************************************/
    public void AppDBInitRoad() {
        SQLiteDatabase db = helper.getReadableDatabase();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT");
        sql.append(" isopen");
        sql.append(" ,level");
        sql.append(" ,stamina");
        sql.append(" ,money");
        sql.append(" ,coin");
        sql.append(" ,gold");
        sql.append(" ,silver");
        sql.append(" ,bronze");
        sql.append(" ,stage");
        sql.append(" ,scoop");
        sql.append(" ,goggles");
        sql.append(" ,ore_1");
        sql.append(" ,ore_2");
        sql.append(" ,ore_3");
        sql.append(" ,item_1");
        sql.append(" ,item_2");
        sql.append(" ,item_3");
        sql.append(" ,syssw_1");
        sql.append(" ,syssw_2");
        sql.append(" ,syssw_3");
        sql.append(" ,syssw_4");
        sql.append(" ,syssw_5");
        sql.append(" FROM appinfo;");
        try {
            Cursor cursor = db.rawQuery(sql.toString(), null);
            //TextViewに表示
            StringBuilder text = new StringBuilder();
            if (cursor.moveToNext()) {
                db_isopen = cursor.getInt(0);
                db_level = cursor.getInt(1);
                db_stamina = cursor.getInt(2);
                db_money = cursor.getInt(3);
                db_coin = cursor.getInt(4);
                db_gold = cursor.getInt(5);
                db_silver = cursor.getInt(6);
                db_bronze = cursor.getInt(7);
                db_stage = cursor.getInt(8);
                db_scoop = cursor.getInt(9);
                db_goggles = cursor.getInt(10);
                db_ore_1 = cursor.getInt(11);
                db_ore_2 = cursor.getInt(12);
                db_ore_3 = cursor.getInt(13);
                db_item_1 = cursor.getInt(14);
                db_item_2 = cursor.getInt(15);
                db_item_3 = cursor.getInt(16);
                db_syssw_1 = cursor.getInt(17);
                db_syssw_2 = cursor.getInt(18);
                db_syssw_3 = cursor.getInt(19);
                db_syssw_4 = cursor.getInt(20);
                db_syssw_5 = cursor.getInt(21);
            }
        } finally {
            db.close();
        }

        db = helper.getWritableDatabase();
        if (db_isopen == 0) {
            long ret;
            /* 新規レコード追加 */
            ContentValues insertValues = new ContentValues();
            insertValues.put("isopen", 1);
            insertValues.put("level", 1);
            insertValues.put("stamina", 100);
            insertValues.put("money", 0);
            insertValues.put("coin", 0);
            insertValues.put("gold", 0);
            insertValues.put("silver", 0);
            insertValues.put("bronze", 0);
            insertValues.put("stage", 0);
            insertValues.put("scoop", 0);
            insertValues.put("goggles", 0);
            insertValues.put("ore_1", 0);
            insertValues.put("ore_2", 0);
            insertValues.put("ore_3", 0);
            insertValues.put("item_1", 0);
            insertValues.put("item_2", 0);
            insertValues.put("item_3", 0);
            insertValues.put("syssw_1", 0);
            insertValues.put("syssw_2", 0);
            insertValues.put("syssw_3", 0);
            insertValues.put("syssw_4", 0);
            insertValues.put("syssw_5", 0);
            try {
                ret = db.insert("appinfo", null, insertValues);
            } finally {
                db.close();
            }
            db_isopen = 1;
            db_level = 1;
            db_stamina = 100;
            /*
            if (ret == -1) {
                Toast.makeText(this, "DataBase Create.... ERROR", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "DataBase Create.... OK", Toast.LENGTH_SHORT).show();
            }
             */
        } else {
            /*
            Toast.makeText(this, "Data Loading...  interval:" + db_interval, Toast.LENGTH_SHORT).show();
             */
        }
    }

    /***************************************************
        DB更新
    ****************************************************/
    public void AppDBUpdated() {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues insertValues = new ContentValues();
        insertValues.put("isopen", db_isopen);
        insertValues.put("level", db_level);
        insertValues.put("stamina", db_stamina);
        insertValues.put("money", db_money);
        insertValues.put("coin", db_coin);
        insertValues.put("gold", db_gold);
        insertValues.put("silver", db_silver);
        insertValues.put("bronze", db_bronze);
        insertValues.put("stage", db_stage);
        insertValues.put("scoop", db_scoop);
        insertValues.put("goggles", db_goggles);
        insertValues.put("ore_1", db_ore_1);
        insertValues.put("ore_2", db_ore_2);
        insertValues.put("ore_3", db_ore_3);
        insertValues.put("item_1", db_item_1);
        insertValues.put("item_2", db_item_2);
        insertValues.put("item_3", db_item_3);
        insertValues.put("syssw_1", db_syssw_1);
        insertValues.put("syssw_2", db_syssw_2);
        insertValues.put("syssw_3", db_syssw_3);
        insertValues.put("syssw_4", db_syssw_4);
        insertValues.put("syssw_5", db_syssw_5);
        int ret;
        try {
            ret = db.update("appinfo", insertValues, null, null);
        } finally {
            db.close();
        }
        /*
        if (ret == -1) {
            Toast.makeText(this, "Saving.... ERROR ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Saving.... OK "+ "op=0:"+db_isopen+" interval=1:"+db_interval+" brightness=2:"+db_brightness, Toast.LENGTH_SHORT).show();
        }
         */
    }

}