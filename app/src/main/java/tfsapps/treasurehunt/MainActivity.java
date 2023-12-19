package tfsapps.treasurehunt;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

    private final Random rand = new Random(System.currentTimeMillis());

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
    private int db_story = 0;               //DB    ストーリー物語の進行
    private int db_data_1 = 0;              //DB    激レア：王冠
    private int db_data_2 = 0;             //DB     指輪
    private int db_data_3 = 0;             //DB     絵画
    private int db_data_4 = 0;             //DB
    private int db_data_5 = 0;             //DB
    private int db_data_6 = 0;             //DB
    private int db_data_7 = 0;             //DB
    private int db_data_8 = 0;             //DB
    private int db_data_9 = 0;             //DB
    private int db_data_10 = 0;             //DB
    private int db_data_11 = 0;             //DB    お宝：コイン
    private int db_data_12 = 0;             //DB    福引き券
    private int db_data_13 = 0;             //DB    スカウトベル
    private int db_data_14 = 0;             //DB
    private int db_data_15 = 0;             //DB
    private int db_data_16 = 0;             //DB
    private int db_data_17 = 0;             //DB
    private int db_data_18 = 0;             //DB
    private int db_data_19 = 0;             //DB
    private int db_data_20 = 0;             //DB    いきもの：お猿
    private int db_data_21 = 0;             //DB    ハト
    private int db_data_22 = 0;             //DB    かに
    private int db_data_23 = 0;             //DB    クジャク
    private int db_data_24 = 0;             //DB    なまけもの
    private int db_data_25 = 0;             //DB    マンボウ
    private int db_data_26 = 0;             //DB    たこ
    private int db_data_27 = 0;             //DB    トナカイ
    private int db_data_28 = 0;             //DB    カラス
    private int db_data_29 = 0;             //DB    パンダ
    private int db_data_30 = 0;             //DB    コアラ
    private int db_data_31 = 0;             //DB    いか
    private int db_data_32 = 0;             //DB    イルカ
    private int db_data_33 = 0;             //DB    ねずみ
    private int db_data_34 = 0;             //DB    ヒヨコ
    private int db_data_35 = 0;             //DB    イノシシ
    private int db_data_36 = 0;             //DB    ゾウ
    private int db_data_37 = 0;             //DB    ぶた
    private int db_data_38 = 0;             //DB    イヌ
    private int db_data_39 = 0;             //DB    ねこ
    private int db_data_40 = 0;             //DB
    private int db_data_41 = 0;             //DB
    private int db_data_42 = 0;             //DB
    private int db_data_43 = 0;             //DB
    private int db_data_44 = 0;             //DB
    private int db_data_45 = 0;             //DB
    private int db_data_46 = 0;             //DB
    private int db_data_47 = 0;             //DB
    private int db_data_48 = 0;             //DB
    private int db_data_49 = 0;             //DB
    private int db_data_50 = 0;             //DB    キャラクター：金太郎
    private int db_data_51 = 0;             //DB    サラリーマン
    private int db_data_52 = 0;             //DB    アイドル
    private int db_data_53 = 0;             //DB    消防士
    private int db_data_54 = 0;             //DB    レンジャー
    private int db_data_55 = 0;             //DB
    private int db_data_56 = 0;             //DB
    private int db_data_57 = 0;             //DB
    private int db_data_58 = 0;             //DB
    private int db_data_59 = 0;             //DB
    private int db_data_60 = 0;             //DB
    private int db_data_61 = 0;             //DB    ガラクタ：やかん
    private int db_data_62 = 0;             //DB    かさ
    private int db_data_63 = 0;             //DB    なべ
    private int db_data_64 = 0;             //DB    新聞紙
    private int db_data_65 = 0;             //DB    乾電池
    private int db_data_66 = 0;             //DB
    private int db_data_67 = 0;             //DB
    private int db_data_68 = 0;             //DB
    private int db_data_69 = 0;             //DB
    private int db_data_70 = 0;             //DB    たべもの：おにぎり
    private int db_data_71 = 0;             //DB    どらやき
    private int db_data_72 = 0;             //DB    とうもころし
    private int db_data_73 = 0;             //DB    フライドポテト
    private int db_data_74 = 0;             //DB    いちご
    private int db_data_75 = 0;             //DB    ケーキ
    private int db_data_76 = 0;             //DB    バナナ
    private int db_data_77 = 0;             //DB    ホットドッグ
    private int db_data_78 = 0;             //DB    ぶどう
    private int db_data_79 = 0;             //DB    大根
    private int db_data_80 = 0;             //DB
    private int db_data_81 = 0;             //DB
    private int db_data_82 = 0;             //DB
    private int db_data_83 = 0;             //DB
    private int db_data_84 = 0;             //DB
    private int db_data_85 = 0;             //DB
    private int db_data_86 = 0;             //DB
    private int db_data_87 = 0;             //DB
    private int db_data_88 = 0;             //DB
    private int db_data_89 = 0;             //DB
    private int db_data_90 = 0;             //DB
    private int db_data_91 = 0;             //DB
    private int db_data_92 = 0;             //DB
    private int db_data_93 = 0;             //DB
    private int db_data_94 = 0;             //DB
    private int db_data_95 = 0;             //DB
    private int db_data_96 = 0;             //DB
    private int db_data_97 = 0;             //DB
    private int db_data_98 = 0;             //DB
    private int db_data_99 = 0;             //DB
    private int db_data_100 = 0;             //DB

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

/*test_make*/

        db_data_1 = 1;
        db_data_2 = 1;
        db_data_3 = 1;
        db_data_11 = 5;
        db_data_12 = 5;
        db_data_13 = 5;
        db_data_20 = 1;
        db_data_21 = 1;
        db_data_22 = 1;
        db_data_23 = 1;
        db_data_24 = 1;
        db_data_25 = 1;
        db_data_26 = 1;
        db_data_27 = 1;
        db_data_28 = 1;
        db_data_29 = 1;
        db_data_30 = 1;
        db_data_31 = 1;
        db_data_32 = 1;
        db_data_33 = 1;
        db_data_34 = 1;
        db_data_35 = 1;
        db_data_36 = 1;
        db_data_37 = 1;
        db_data_38 = 1;
        db_data_39 = 1;
        db_data_61 = 1;
        db_data_62 = 1;
        db_data_63 = 1;
        db_data_64 = 1;
        db_data_65 = 1;
        db_data_70 = 1;
        db_data_71 = 1;
        db_data_72 = 1;
        db_data_73 = 1;
        db_data_74 = 1;
        db_data_75 = 1;
        db_data_76 = 1;
        db_data_77 = 1;
        db_data_78 = 1;
        db_data_79 = 1;
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

        /*
        List<String> providers = locationManager.getProviders(true);
        // 並行して、３のプロバイダーを起動する
        for (String provider : providers){
            locationManager.requestLocationUpdates(provider,
                    1000, 3, this);
        }
         */
        /*
        //  屋外はGPS_PEOVIDER
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                3000, 5, this);
         */
        //  屋外はGPS_PEOVIDER

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                3000, 3, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                5000, 5, this);
        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,
                5000, 7, this);

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
         物語
     ************************************************/
    public void onGameStory(View v) {
        MyStory();
    }
    /************************************************
        ゲームスタート
     ************************************************/
    public void onGameScreen(View v){
        String message = "";
        if (db_story == 0){
            message += "\n「物語」を始めて下さい\n\n\n\n";
            CustomDialog.showCustomDialog(this, R.drawable.info, message, 0);
            return;
        }

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
            txtstatus.setText("「START」を押して 宝探し を始める");
            btn.setText("START");
        }
        else{
            String tmp ="";
            if (myMap.isHitTreasure()) {
                tmp += "\n「スコップ」ボタンで穴掘り開始\n";
            } else {
                tmp += "\nお宝？の場所まで移動しよう・・・\n";
            }
            tmp += "\n赤：はじめの位置　青：いまの位置　" +
                   "\n黄：お宝？の場所　緑：穴掘り開始　" +
                   "";

            txtstatus.setText(tmp);
            btn.setText("END");
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

            locationStart();

            //タイマーインスタンス生成
            this.mainTimer1 = new Timer();
            //タスククラスインスタンス生成
            this.mainTimerTask1 = new MainTimerTask();
            //タイマースケジュール設定＆開始
            //      this.mainTimer1.schedule(mainTimerTask1, 5000, 10000);
            //      this.mainTimer1.schedule(mainTimerTask1, 1000, 5000);
            this.mainTimer1.schedule(mainTimerTask1, 0, 100);
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
        if (this.mainTimer1 == null) {
            return;
        }
        String message = "";
        int id = 0;
        if (myMap.isHitTreasure()){
            id = R.drawable.start;
            message +=
                    "【穴掘り】スタート\n\n"+
                    " ・・・お宝ザクザク・・・\n"+
                    "\n\n\n";
            CustomDialog.showCustomDialog(this, id, message, 1);
//           ScoopResult();
        }
        else{
        }

    }
    //結果
    public void ScoopResult(){
        int type = myMap.ZakuZakuResult();
        //スタミナ減少
        db_stamina -= 10;
        if (db_stamina < 0){
            db_stamina = 0;
        }
        String message = "";
        int id = 0;
        int step = 0;
        switch (type) {
            case 1:
                id = R.drawable.t01;
                message += "神キター！！\n\n\nレア宝「王冠」を入手しました\n\n";
                break;
            case 2:
                id = R.drawable.t02;
                message += "神キター！！\n\n\nレア宝「指輪」を入手しました\n\n";
                break;
            case 3:
                id = R.drawable.t03;
                message += "神キター！！\n\n\nレア宝「絵画」を入手しました\n\n";
                break;
            case 11:
                id = R.drawable.t11;
                message += "やったー！！\n\n\nお宝「コイン」を入手しました\n\n";
                break;
            case 12:
                step = 11;
                id = R.drawable.t12;
                message += "やったー！！\n\n\nお宝「福引き券」を入手しました\n\n";
                break;
            case 13:
                step = 21;
                id = R.drawable.t13;
                message += "やったー！！\n\n\nお宝「スカウトベル」を入手しました\n\n";
                break;
            case 61:
                id = R.drawable.t61;
                message += "ざんねん\n\n\nガラクタ「やかん」を入手しました\n\n";
                break;
            case 62:
                id = R.drawable.t62;
                message += "ざんねん\n\n\nガラクタ「かさ」を入手しました\n\n";
                break;
            case 63:
                id = R.drawable.t63;
                message += "ざんねん\n\n\nガラクタ「なべ」を入手しました\n\n";
                break;
            case 64:
                id = R.drawable.t64;
                message += "ざんねん\n\n\nガラクタ「新聞紙」を入手しました\n\n";
                break;
            case 65:
                id = R.drawable.t65;
                message += "ざんねん\n\n\nガラクタ「乾電池」を入手しました\n\n";
                break;
        }
        CustomDialog.showCustomDialog(this, id, message, step);
        if (step == 0){
            //入手PLUS
            SetGameDbParam(type, 1);
        }
    }

    /************************************************
         福引き券　を使用してアイテム入手
     ************************************************/
    public void LotDone(){
        String message = "";
        int id = 0;
        id = R.drawable.t12;
        message +=
                "【福引き券ｘ1】を使用して\nお宝GETだぜ\n\n"+
                        " ・・・何が出るかなぁ？・・・\n"+
                        "\n\n\n";
        CustomDialog.showCustomDialog(this, id, message, 12);
    }
    //結果
    public void LotResult() {
        String message = "";
        int id = 0;
        int index;
        String name = "";
        index = TreasureSelectLot();
        id = GetGameIcon(index);
        name = GetGameDataName(index);
        message += "やったー！！\n\n\nお宝「" +name + "」を入手しました\n\n";
        CustomDialog.showCustomDialog(this, id, message, 0);

        //福引MINUS
        SetGameDbParam(12, -1);
        //入手PLUS
        SetGameDbParam(index, 1);
    }
    /************************************************
        スカウトベル　を使用してアイテム入手
     ************************************************/
    public void ScoutDone(){
        String message = "";
        int id = 0;
        id = R.drawable.t13;
        message +=
                "【スカウトベルｘ1】を使用して\n仲間GETだぜ\n\n"+
                        " ・・・だれが来るかなぁ？・・・\n"+
                        "\n\n\n";
        CustomDialog.showCustomDialog(this, id, message, 22);
    }
    //結果
    public void ScoutResult() {
        String message = "";
        int id = 0;
        int index;
        String name = "";
        index = TreasureSelectScoutBell();
        id = GetGameIcon(index);
        name = GetGameDataName(index);
        message += "やったー！！\n\n\n「" +name + "」が仲間に加わりました\n\n";
        CustomDialog.showCustomDialog(this, id, message, 0);

        //ベルMINUS
        SetGameDbParam(13, -1);
        //入手PLUS
        SetGameDbParam(index, 1);
    }


    /************************************************
        ストーリー
     ************************************************/
    public void MyStory() {
        String message = "";
        int id = 0;
        int story = db_story;
        int step = 0;
        int count = 0;

        if (db_data_20 > 0) count++;
        if (db_data_21 > 0) count++;
        if (db_data_22 > 0) count++;
        if (db_data_23 > 0) count++;
        if (db_data_24 > 0) count++;
        if (db_data_25 > 0) count++;
        if (db_data_26 > 0) count++;
        if (db_data_27 > 0) count++;
        if (db_data_28 > 0) count++;
        if (db_data_29 > 0) count++;
        if (db_data_30 > 0) count++;
        if (db_data_31 > 0) count++;
        if (db_data_32 > 0) count++;
        if (db_data_33 > 0) count++;
        if (db_data_34 > 0) count++;
        if (db_data_35 > 0) count++;
        if (db_data_36 > 0) count++;
        if (db_data_37 > 0) count++;
        if (db_data_38 > 0) count++;
        if (db_data_39 > 0) count++;

        if (db_story == 4){
            // 鬼退治
            if (count >=10){
                db_story = 5;
            }
        }

        switch (story){
            /* 冒険の始まり */
            case 0:
                id = R.drawable.s002;    step = 1;   db_story++;
                message +=
                        "王様：「勇者よ、そなたは今日で16才になった」\n" +
                        "王様：「そこでお願いがある」\n" +
                        "王様：「村人のため【鬼退治】をお願いしたい」\n\n";
                break;
            case 1:
                id = R.drawable.s001;    step = 1;   db_story++;
                message +=
                        "勇者：「王様、私には無理です」\n" +
                        "勇者：「私が臆病なのをご存知のはず・・・」\n" +
                        "\n\n";
                break;
            case 2:
                id = R.drawable.s002;    step = 1;   db_story++;
                message +=
                        "王様：「勇者よ、わかった」\n" +
                        "王様：「そなたに【宝の地図、スコップ】を渡す」\n" +
                        "王様：「穴を掘って宝を集め」\n" +
                        "王様：「そしてその宝で仲間を集めるのだ」\n\n";
                break;
            case 3:
                id = R.drawable.s001;    step = 0;  db_story++;
                message +=
                        "勇者：「王様、やっ・・・やってみます」\n" +
                        "勇者：「まずは仲間を【10】集めます」\n" +
                        "勇者：「そして仲間と【鬼退治】へ行きます」\n" +
                        "\n\n";
                break;
            case 4:
                id = R.drawable.s001;    step = 0;
                message +=
                        "勇者：「仲間は今のところ【"+count+"】だなぁ」\n" +
                        "勇者：「鬼を退治には仲間は【10】必要だ」\n" +
                        "勇者：「宝探しを続けよう」\n" +
                        "\n\n";
                break;
            case 5:
                id = R.drawable.s001;    step = 1;  db_story++;
                message +=
                        "勇者：「王様、仲間が【"+count+"】集まりました」\n" +
                        "勇者：「鬼を退治に行きます」\n" +
                        "\n" +
                        "\n\n";
                break;
            case 6:
                id = R.drawable.s002;    step = 1;  db_story++;
                message +=
                        "王様：「勇者よ、強そうな仲間たちじゃ」\n" +
                        "王様：「村人のため鬼退治をたのむ」\n" +
                        "\n" +
                        "\n\n";
                break;
            case 7:
                id = R.drawable.s001;    step = 1;  db_story++;
                message +=
                        "勇者：「では行ってきます！！」\n" +
                        "\n" +
                        "・・・こうして・・・勇者たちは鬼の所へ\n" +
                        "\n\n";
                break;
            case 8:
                id = R.drawable.s003;    step = 1;  db_story++;
                message +=
                        "赤鬼：「誰だー！！我を起こす者は？」\n" +
                        "赤鬼：「弱い人間が我に勝てるのか？」\n" +
                        "\n" +
                        "\n\n";
                break;
            case 9:
                id = R.drawable.s001;    step = 1;  db_story++;
                message +=
                        "勇者：「こっ恐い〜〜〜汗」\n" +
                        "勇者：「仲間たちに頼むしかない」\n" +
                        "勇者：「みんな鬼退治をよろしく！！」\n" +
                        "\n\n";
                break;
            case 10:
                id = R.drawable.s001;    step = 1;  db_story++;
                message +=
                        "・・・ドドドドォー！！・・・\n" +
                        "（仲間たちが一斉に鬼へ飛び込んだ）\n" +
                        "\n" +
                        "\n\n";
                break;
            case 11:
                id = R.drawable.s003;    step = 1;  db_story++;
                message +=
                        "赤鬼：「ひぇー！！敵わない」\n" +
                        "赤鬼：「逃げろぉ・・・・」\n" +
                        "（鬼は泣きながら逃げていく）\n" +
                        "\n\n";
                break;
            case 12:
                id = R.drawable.s001;    step = 1;  db_story++;
                message +=
                        "勇者：「やったー！！鬼を退治できた」\n" +
                        "勇者：「みんなありがとう♪」\n\n" +
                        "・・・（勇者は王様の所へ）・・・\n" +
                        "\n";
                break;
            case 13:
                id = R.drawable.s002;    step = 1;  db_story++;
                message +=
                        "王様：「勇者とその仲間たち・・・」\n" +
                        "王様：「鬼退治ありがとう！！」\n" +
                        "王様：「村人たちも安心じゃ」\n" +
                        "\n\n";
                break;
            case 14:
                id = R.drawable.info;    step = 0;
                message +=
                        "・・・おめでとう！！・・・\n" +
                        "「鬼退治」編クリアです\n" +
                        "\n"+
                        "まだ見つけていない\n"+
                        "激レア・アイテム・仲間を集めてみては？\n" +
                        "\n\n";
                break;
            default:
                break;
        }
        CustomDialog.showCustomDialogStory(this, id, message, 1, step);
        return;
    }


    /************************************************
        買い物（ショップ）
     ************************************************/
    public void onShop(View v){
        //サブ画面へ移動
        setContentView(R.layout.list_item);
        ListItemDisp();
    }
    /************************************************
        仲間（スカウト）
     ************************************************/
    public void onScout(View v){
        //サブ画面へ移動
        setContentView(R.layout.list_scout);
        ListScoutDisp();
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
                    if (get_GPS == false) {
                        locationStart();
                    }
                    SubShow();
                }
            });
        }
    }

    /************************************************
        お宝  表示処理
     ************************************************/
    public void ListItemDisp(){
        String temp = "";
        TextView t0101 = findViewById(R.id.text_name_list0101);
        TextView t0102 = findViewById(R.id.text_name_list0102);
        ImageView i0101 = findViewById(R.id.img_list0101);
        ImageView i0102 = findViewById(R.id.img_list0102);
        if (db_data_1 > 0){
            temp = "神レア\n" + GetGameDataName(1) + "ｘ" + db_data_1;
            t0101.setText(temp);    i0101.setImageResource(R.drawable.t01);
        }
        if (db_data_2 > 0){
            temp = "神レア\n" + GetGameDataName(2) + "ｘ" + db_data_2;
            t0102.setText(temp);    i0102.setImageResource(R.drawable.t02);
        }

        TextView t0201 = findViewById(R.id.text_name_list0201);
        TextView t0202 = findViewById(R.id.text_name_list0202);
        ImageView i0201 = findViewById(R.id.img_list0201);
        ImageView i0202 = findViewById(R.id.img_list0202);
        if (db_data_3 > 0){
            temp = "神レア\n" + GetGameDataName(3) + "ｘ" + db_data_3;
            t0201.setText(temp);    i0201.setImageResource(R.drawable.t03);
        }
        if (db_data_4 > 0){
            temp = GetGameDataName(4);
            t0202.setText(temp);    i0202.setImageResource(R.drawable.t999);
        }

        TextView t0301 = findViewById(R.id.text_name_list0301);
        TextView t0302 = findViewById(R.id.text_name_list0302);
        ImageView i0301 = findViewById(R.id.img_list0301);
        ImageView i0302 = findViewById(R.id.img_list0302);
        if (db_data_11 > 0){
            temp = GetGameDataName(11) + "ｘ" + db_data_11;
            t0301.setText(temp);    i0301.setImageResource(R.drawable.t11);
        }
        if (db_data_12 > 0){
            temp = GetGameDataName(12) + "ｘ" + db_data_12;
            t0302.setText(temp);    i0302.setImageResource(R.drawable.t12);
        }

        TextView t0401 = findViewById(R.id.text_name_list0401);
        TextView t0402 = findViewById(R.id.text_name_list0402);
        ImageView i0401 = findViewById(R.id.img_list0401);
        ImageView i0402 = findViewById(R.id.img_list0402);
        if (db_data_13 > 0){
            temp = GetGameDataName(13) + "ｘ" + db_data_13;
            t0401.setText(temp);    i0401.setImageResource(R.drawable.t13);
        }
        if (db_data_14 > 0){
            temp = GetGameDataName(14);
            t0402.setText(temp);    i0402.setImageResource(R.drawable.t999);
        }

        TextView t0501 = findViewById(R.id.text_name_list0501);
        TextView t0502 = findViewById(R.id.text_name_list0502);
        ImageView i0501 = findViewById(R.id.img_list0501);
        ImageView i0502 = findViewById(R.id.img_list0502);
        if (db_data_61 > 0){
            temp = GetGameDataName(61) + "ｘ" + db_data_61;
            t0501.setText(temp);    i0501.setImageResource(R.drawable.t61);
        }
        if (db_data_62 > 0){
            temp = GetGameDataName(62) + "ｘ" + db_data_62;
            t0502.setText(temp);    i0502.setImageResource(R.drawable.t62);
        }

        TextView t0601 = findViewById(R.id.text_name_list0601);
        TextView t0602 = findViewById(R.id.text_name_list0602);
        ImageView i0601 = findViewById(R.id.img_list0601);
        ImageView i0602 = findViewById(R.id.img_list0602);
        if (db_data_63 > 0){
            temp = GetGameDataName(63) + "ｘ" + db_data_63;
            t0601.setText(temp);    i0601.setImageResource(R.drawable.t63);
        }
        if (db_data_64 > 0){
            temp = GetGameDataName(64) + "ｘ" + db_data_64;
            t0602.setText(temp);    i0602.setImageResource(R.drawable.t64);
        }

        TextView t0701 = findViewById(R.id.text_name_list0701);
        TextView t0702 = findViewById(R.id.text_name_list0702);
        ImageView i0701 = findViewById(R.id.img_list0701);
        ImageView i0702 = findViewById(R.id.img_list0702);
        if (db_data_65 > 0){
            temp = GetGameDataName(65) + "ｘ" + db_data_65;
            t0701.setText(temp);    i0701.setImageResource(R.drawable.t65);
        }
        if (db_data_66 > 0){
            temp = GetGameDataName(66);
            t0702.setText(temp);    i0702.setImageResource(R.drawable.t999);
        }

        TextView t0801 = findViewById(R.id.text_name_list0801);
        TextView t0802 = findViewById(R.id.text_name_list0802);
        ImageView i0801 = findViewById(R.id.img_list0801);
        ImageView i0802 = findViewById(R.id.img_list0802);
        if (db_data_71 > 0){
            temp = GetGameDataName(71) + "ｘ" + db_data_71;
            t0801.setText(temp);    i0801.setImageResource(R.drawable.t71);
        }
        if (db_data_72 > 0){
            temp = GetGameDataName(72) + "ｘ" + db_data_72;
            t0802.setText(temp);    i0802.setImageResource(R.drawable.t72);
        }

        TextView t0901 = findViewById(R.id.text_name_list0901);
        TextView t0902 = findViewById(R.id.text_name_list0902);
        ImageView i0901 = findViewById(R.id.img_list0901);
        ImageView i0902 = findViewById(R.id.img_list0902);
        if (db_data_73 > 0){
            temp = GetGameDataName(73) + "ｘ" + db_data_73;
            t0901.setText(temp);    i0901.setImageResource(R.drawable.t73);
        }
        if (db_data_74 > 0){
            temp = GetGameDataName(74) + "ｘ" + db_data_74;
            t0902.setText(temp);    i0902.setImageResource(R.drawable.t74);
        }

        TextView t1001 = findViewById(R.id.text_name_list1001);
        TextView t1002 = findViewById(R.id.text_name_list1002);
        ImageView i1001 = findViewById(R.id.img_list1001);
        ImageView i1002 = findViewById(R.id.img_list1002);
        if (db_data_75 > 0){
            temp = GetGameDataName(75) + "ｘ" + db_data_75;
            t1001.setText(temp);    i1001.setImageResource(R.drawable.t75);
        }
        if (db_data_76 > 0){
            temp = GetGameDataName(76) + "ｘ" + db_data_76;
            t1002.setText(temp);    i1002.setImageResource(R.drawable.t76);
        }

        TextView t1101 = findViewById(R.id.text_name_list1101);
        TextView t1102 = findViewById(R.id.text_name_list1102);
        ImageView i1101 = findViewById(R.id.img_list1101);
        ImageView i1102 = findViewById(R.id.img_list1102);
        if (db_data_77 > 0){
            temp = GetGameDataName(77) + "ｘ" + db_data_77;
            t1101.setText(temp);    i1101.setImageResource(R.drawable.t77);
        }
        if (db_data_78 > 0){
            temp = GetGameDataName(78) + "ｘ" + db_data_78;
            t1102.setText(temp);    i1102.setImageResource(R.drawable.t78);
        }

        TextView t1201 = findViewById(R.id.text_name_list1201);
        TextView t1202 = findViewById(R.id.text_name_list1202);
        ImageView i1201 = findViewById(R.id.img_list1201);
        ImageView i1202 = findViewById(R.id.img_list1202);
        if (db_data_79 > 0){
            temp = GetGameDataName(79) + "ｘ" + db_data_79;
            t1201.setText(temp);    i1201.setImageResource(R.drawable.t79);
        }
        if (db_data_80 > 0){
            temp = GetGameDataName(80);
            t1202.setText(temp);    i1202.setImageResource(R.drawable.t999);
        }

    }
    /************************************************
        お宝  メニューボタン処理
     ************************************************/
    public void onItemMenu(View v){
        setContentView(R.layout.activity_main);
    }


    /************************************************
         仲間（スカウト）表示処理
     ************************************************/
    public void ListScoutDisp(){
        TextView t0101 = findViewById(R.id.text_name_list0101);
        TextView t0102 = findViewById(R.id.text_name_list0102);
        ImageView i0101 = findViewById(R.id.img_list0101);
        ImageView i0102 = findViewById(R.id.img_list0102);
        if (db_data_20 > 0){
            t0101.setText(GetGameDataName(20));    i0101.setImageResource(R.drawable.c20);
        }
        if (db_data_21 > 0){
            t0102.setText(GetGameDataName(21));    i0102.setImageResource(R.drawable.c21);
        }

        TextView t0201 = findViewById(R.id.text_name_list0201);
        TextView t0202 = findViewById(R.id.text_name_list0202);
        ImageView i0201 = findViewById(R.id.img_list0201);
        ImageView i0202 = findViewById(R.id.img_list0202);
        if (db_data_22 > 0){
            t0201.setText(GetGameDataName(22));    i0201.setImageResource(R.drawable.c22);
        }
        if (db_data_23 > 0){
            t0202.setText(GetGameDataName(23));    i0202.setImageResource(R.drawable.c23);
        }

        TextView t0301 = findViewById(R.id.text_name_list0301);
        TextView t0302 = findViewById(R.id.text_name_list0302);
        ImageView i0301 = findViewById(R.id.img_list0301);
        ImageView i0302 = findViewById(R.id.img_list0302);
        if (db_data_24 > 0){
            t0301.setText(GetGameDataName(24));    i0301.setImageResource(R.drawable.c24);
        }
        if (db_data_25 > 0){
            t0302.setText(GetGameDataName(25));    i0302.setImageResource(R.drawable.c25);
        }

        TextView t0401 = findViewById(R.id.text_name_list0401);
        TextView t0402 = findViewById(R.id.text_name_list0402);
        ImageView i0401 = findViewById(R.id.img_list0401);
        ImageView i0402 = findViewById(R.id.img_list0402);
        if (db_data_26 > 0){
            t0401.setText(GetGameDataName(26));    i0401.setImageResource(R.drawable.c26);
        }
        if (db_data_27 > 0){
            t0402.setText(GetGameDataName(27));    i0402.setImageResource(R.drawable.c27);
        }

        TextView t0501 = findViewById(R.id.text_name_list0501);
        TextView t0502 = findViewById(R.id.text_name_list0502);
        ImageView i0501 = findViewById(R.id.img_list0501);
        ImageView i0502 = findViewById(R.id.img_list0502);
        if (db_data_28 > 0){
            t0501.setText(GetGameDataName(28));    i0501.setImageResource(R.drawable.c28);
        }
        if (db_data_29 > 0){
            t0502.setText(GetGameDataName(29));    i0502.setImageResource(R.drawable.c29);
        }

        TextView t0601 = findViewById(R.id.text_name_list0601);
        TextView t0602 = findViewById(R.id.text_name_list0602);
        ImageView i0601 = findViewById(R.id.img_list0601);
        ImageView i0602 = findViewById(R.id.img_list0602);
        if (db_data_30 > 0){
            t0601.setText(GetGameDataName(30));    i0601.setImageResource(R.drawable.c30);
        }
        if (db_data_31 > 0){
            t0602.setText(GetGameDataName(31));    i0602.setImageResource(R.drawable.c31);
        }

        TextView t0701 = findViewById(R.id.text_name_list0701);
        TextView t0702 = findViewById(R.id.text_name_list0702);
        ImageView i0701 = findViewById(R.id.img_list0701);
        ImageView i0702 = findViewById(R.id.img_list0702);
        if (db_data_32 > 0){
            t0701.setText(GetGameDataName(32));    i0701.setImageResource(R.drawable.c32);
        }
        if (db_data_33 > 0){
            t0702.setText(GetGameDataName(33));    i0702.setImageResource(R.drawable.c33);
        }

        TextView t0801 = findViewById(R.id.text_name_list0801);
        TextView t0802 = findViewById(R.id.text_name_list0802);
        ImageView i0801 = findViewById(R.id.img_list0801);
        ImageView i0802 = findViewById(R.id.img_list0802);
        if (db_data_34 > 0){
            t0801.setText(GetGameDataName(34));    i0801.setImageResource(R.drawable.c34);
        }
        if (db_data_35 > 0){
            t0802.setText(GetGameDataName(35));    i0802.setImageResource(R.drawable.c35);
        }

        TextView t0901 = findViewById(R.id.text_name_list0901);
        TextView t0902 = findViewById(R.id.text_name_list0902);
        ImageView i0901 = findViewById(R.id.img_list0901);
        ImageView i0902 = findViewById(R.id.img_list0902);
        if (db_data_36 > 0){
            t0901.setText(GetGameDataName(36));    i0901.setImageResource(R.drawable.c36);
        }
        if (db_data_37 > 0){
            t0902.setText(GetGameDataName(37));    i0902.setImageResource(R.drawable.c37);
        }

        TextView t1001 = findViewById(R.id.text_name_list1001);
        TextView t1002 = findViewById(R.id.text_name_list1002);
        ImageView i1001 = findViewById(R.id.img_list1001);
        ImageView i1002 = findViewById(R.id.img_list1002);
        if (db_data_38 > 0){
            t1001.setText(GetGameDataName(38));    i1001.setImageResource(R.drawable.c38);
        }
        if (db_data_39 > 0){
            t1002.setText(GetGameDataName(39));    i1002.setImageResource(R.drawable.c39);
        }


    }
    /************************************************
         仲間（スカウト）メニューボタン処理
     ************************************************/
    public void onScoutMenu(View v){
        setContentView(R.layout.activity_main);
    }


    /************************************************
         ゲームのアイテム、キャラクターの名前取得
     ************************************************/
    public String GetGameDataName(int index){
        String name = "？？？";

        switch (index){
            case 1: name = "王冠"; break;
            case 2: name = "指輪"; break;
            case 3: name = "絵画"; break;
            case 11: name = "コイン"; break;
            case 12: name = "福引き券"; break;
            case 13: name = "スカウトベル"; break;
            case 20: name = "お猿"; break;
            case 21: name = "ハト"; break;
            case 22: name = "かに"; break;
            case 23: name = "クジャク"; break;
            case 24: name = "なまけもの"; break;
            case 25: name = "マンボウ"; break;
            case 26: name = "タコ"; break;
            case 27: name = "トナカイ"; break;
            case 28: name = "カラス"; break;
            case 29: name = "パンダ"; break;
            case 30: name = "コアラ"; break;
            case 31: name = "イカ"; break;
            case 32: name = "イルカ"; break;
            case 33: name = "ねずみ"; break;
            case 34: name = "ヒヨコ"; break;
            case 35: name = "イノシシ"; break;
            case 36: name = "ゾウ"; break;
            case 37: name = "ぶた"; break;
            case 38: name = "イヌ"; break;
            case 39: name = "ねこ"; break;
            case 61: name = "やかん"; break;
            case 62: name = "かさ"; break;
            case 63: name = "なべ"; break;
            case 64: name = "新聞紙"; break;
            case 65: name = "乾電池"; break;
            case 70: name = "おにぎり"; break;
            case 71: name = "どらやき"; break;
            case 72: name = "とうもろこし"; break;
            case 73: name = "フライドポテト"; break;
            case 74: name = "いちご"; break;
            case 75: name = "ケーキ"; break;
            case 76: name = "バナナ"; break;
            case 77: name = "ホットドッグ"; break;
            case 78: name = "ぶどう"; break;
            case 79: name = "大根"; break;
        }

        return name;
    }
    /************************************************
     ゲームのアイテム、キャラクターの icon image 取得
     ************************************************/
    public int GetGameIcon(int index){
        int icon = R.drawable.t999;

        switch (index){
            case 1: icon = R.drawable.t01;  break;
            case 2: icon = R.drawable.t02;  break;
            case 3: icon = R.drawable.t03;  break;
            case 11: icon = R.drawable.t11;  break;
            case 12: icon = R.drawable.t12;  break;
            case 13: icon = R.drawable.t13;  break;
            case 20: icon = R.drawable.c20;  break;
            case 21: icon = R.drawable.c21;  break;
            case 22: icon = R.drawable.c22;  break;
            case 23: icon = R.drawable.c23;  break;
            case 24: icon = R.drawable.c24;  break;
            case 25: icon = R.drawable.c25;  break;
            case 26: icon = R.drawable.c26;  break;
            case 27: icon = R.drawable.c27;  break;
            case 28: icon = R.drawable.c28;  break;
            case 29: icon = R.drawable.c29;  break;
            case 30: icon = R.drawable.c30;  break;
            case 31: icon = R.drawable.c31;  break;
            case 32: icon = R.drawable.c32;  break;
            case 33: icon = R.drawable.c33;  break;
            case 34: icon = R.drawable.c34;  break;
            case 35: icon = R.drawable.c35;  break;
            case 36: icon = R.drawable.c36;  break;
            case 37: icon = R.drawable.c37;  break;
            case 38: icon = R.drawable.c38;  break;
            case 39: icon = R.drawable.c39;  break;
            case 61: icon = R.drawable.t61;  break;
            case 62: icon = R.drawable.t62;  break;
            case 63: icon = R.drawable.t63;  break;
            case 64: icon = R.drawable.t64;  break;
            case 65: icon = R.drawable.t65;  break;
            case 70: icon = R.drawable.t70;  break;
            case 71: icon = R.drawable.t71;  break;
            case 72: icon = R.drawable.t72;  break;
            case 73: icon = R.drawable.t73;  break;
            case 74: icon = R.drawable.t74;  break;
            case 75: icon = R.drawable.t75;  break;
            case 76: icon = R.drawable.t76;  break;
            case 77: icon = R.drawable.t77;  break;
            case 78: icon = R.drawable.t78;  break;
            case 79: icon = R.drawable.t79;  break;
        }

        return icon;
    }

    /************************************************
         ゲームデータのデータアクセス
     ************************************************/
    public void SetGameDbParam(int index, int data) {

        switch (index) {
            case 1: db_data_1 += data; break;
            case 2: db_data_2 += data; break;
            case 3: db_data_3 += data; break;
            case 11: db_data_11 += data; break;
            case 12: db_data_12 += data; break;
            case 13: db_data_13 += data; break;
            case 20: db_data_20 += data; break;
            case 21: db_data_21 += data; break;
            case 22: db_data_22 += data; break;
            case 23: db_data_23 += data; break;
            case 24: db_data_24 += data; break;
            case 25: db_data_25 += data; break;
            case 26: db_data_26 += data; break;
            case 27: db_data_27 += data; break;
            case 28: db_data_28 += data; break;
            case 29: db_data_29 += data; break;
            case 30: db_data_30 += data; break;
            case 31: db_data_31 += data; break;
            case 32: db_data_32 += data; break;
            case 33: db_data_33 += data; break;
            case 34: db_data_34 += data; break;
            case 35: db_data_35 += data; break;
            case 36: db_data_36 += data; break;
            case 37: db_data_37 += data; break;
            case 38: db_data_38 += data; break;
            case 39: db_data_39 += data; break;
            case 61: db_data_61 += data; break;
            case 62: db_data_62 += data; break;
            case 63: db_data_63 += data; break;
            case 64: db_data_64 += data; break;
            case 65: db_data_65 += data; break;
            case 70: db_data_70 += data; break;
            case 71: db_data_71 += data; break;
            case 72: db_data_72 += data; break;
            case 73: db_data_73 += data; break;
            case 74: db_data_74 += data; break;
            case 75: db_data_75 += data; break;
            case 76: db_data_76 += data; break;
            case 77: db_data_77 += data; break;
            case 78: db_data_78 += data; break;
            case 79: db_data_79 += data; break;
            default:break;
        }
    }

    /***************************************************
         福引券
         お宝確率
         t70     おにぎり
         t71     どらやき
         t72     とうもろこし
         t73     フライドポテト
         t74     イチゴ
         t75     ケーキ
         t76     バナナ
         t77     ホットドッグ
         t78     ぶどう
         t79     大根
     ****************************************************/
    public int TreasureSelectLot() {
        int type = 0;
        /* 一旦、たべもの１０個とする */
        type = rand.nextInt(10);
        type += 70;
        if (type == 80){
            type = 79;
        }
        return type;
    }

    /***************************************************
         スカウトベル
         お宝確率
         C051    金太郎
         C052    サラリーマン
         C053    アイドル
         C054    消防士
         C055    レンジャー

         C20     お猿
         C21     ハト
         C22     かに
         C23     クジャク
         C24     なまけもの
         C25     マンボウ
         C26     たこ
         C27     トナカイ
         C28     カラス
         C29     パンダ
         C30     コアラ
         C31     いか
         C32     イルカ
         C33     ねずみ
         C34     ヒヨコ
         C35     イノシシ
         C36     ゾウ
         C37     ぶた
         C38     イヌ
         C39     ネコ
     ****************************************************/
    public int TreasureSelectScoutBell() {
        int type = 0;

        /* 一旦、いきもの２０個とする */
        type = rand.nextInt(20);
        type += 20;
        if (type == 40){
            type = 39;
        }
        return type;
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
        sql.append(" ,story");
        sql.append(" ,data_1");
        sql.append(" ,data_2");
        sql.append(" ,data_3");
        sql.append(" ,data_4");
        sql.append(" ,data_5");
        sql.append(" ,data_6");
        sql.append(" ,data_7");
        sql.append(" ,data_8");
        sql.append(" ,data_9");
        sql.append(" ,data_10");
        sql.append(" ,data_11");
        sql.append(" ,data_12");
        sql.append(" ,data_13");
        sql.append(" ,data_14");
        sql.append(" ,data_15");
        sql.append(" ,data_16");
        sql.append(" ,data_17");
        sql.append(" ,data_18");
        sql.append(" ,data_19");
        sql.append(" ,data_20");
        sql.append(" ,data_21");
        sql.append(" ,data_22");
        sql.append(" ,data_23");
        sql.append(" ,data_24");
        sql.append(" ,data_25");
        sql.append(" ,data_26");
        sql.append(" ,data_27");
        sql.append(" ,data_28");
        sql.append(" ,data_29");
        sql.append(" ,data_30");
        sql.append(" ,data_31");
        sql.append(" ,data_32");
        sql.append(" ,data_33");
        sql.append(" ,data_34");
        sql.append(" ,data_35");
        sql.append(" ,data_36");
        sql.append(" ,data_37");
        sql.append(" ,data_38");
        sql.append(" ,data_39");
        sql.append(" ,data_40");
        sql.append(" ,data_41");
        sql.append(" ,data_42");
        sql.append(" ,data_43");
        sql.append(" ,data_44");
        sql.append(" ,data_45");
        sql.append(" ,data_46");
        sql.append(" ,data_47");
        sql.append(" ,data_48");
        sql.append(" ,data_49");
        sql.append(" ,data_50");
        sql.append(" ,data_51");
        sql.append(" ,data_52");
        sql.append(" ,data_53");
        sql.append(" ,data_54");
        sql.append(" ,data_55");
        sql.append(" ,data_56");
        sql.append(" ,data_57");
        sql.append(" ,data_58");
        sql.append(" ,data_59");
        sql.append(" ,data_60");
        sql.append(" ,data_61");
        sql.append(" ,data_62");
        sql.append(" ,data_63");
        sql.append(" ,data_64");
        sql.append(" ,data_65");
        sql.append(" ,data_66");
        sql.append(" ,data_67");
        sql.append(" ,data_68");
        sql.append(" ,data_69");
        sql.append(" ,data_70");
        sql.append(" ,data_71");
        sql.append(" ,data_72");
        sql.append(" ,data_73");
        sql.append(" ,data_74");
        sql.append(" ,data_75");
        sql.append(" ,data_76");
        sql.append(" ,data_77");
        sql.append(" ,data_78");
        sql.append(" ,data_79");
        sql.append(" ,data_80");
        sql.append(" ,data_81");
        sql.append(" ,data_82");
        sql.append(" ,data_83");
        sql.append(" ,data_84");
        sql.append(" ,data_85");
        sql.append(" ,data_86");
        sql.append(" ,data_87");
        sql.append(" ,data_88");
        sql.append(" ,data_89");
        sql.append(" ,data_90");
        sql.append(" ,data_91");
        sql.append(" ,data_92");
        sql.append(" ,data_93");
        sql.append(" ,data_94");
        sql.append(" ,data_95");
        sql.append(" ,data_96");
        sql.append(" ,data_97");
        sql.append(" ,data_98");
        sql.append(" ,data_99");
        sql.append(" ,data_100");
        sql.append(" FROM appinfo2;");
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
                db_story = cursor.getInt(22);
                db_data_1 = cursor.getInt(23);
                db_data_2 = cursor.getInt(24);
                db_data_3 = cursor.getInt(25);
                db_data_4 = cursor.getInt(26);
                db_data_5 = cursor.getInt(27);
                db_data_6 = cursor.getInt(28);
                db_data_7 = cursor.getInt(29);
                db_data_8 = cursor.getInt(30);
                db_data_9 = cursor.getInt(31);
                db_data_10 = cursor.getInt(32);
                db_data_11 = cursor.getInt(33);
                db_data_12 = cursor.getInt(34);
                db_data_13 = cursor.getInt(35);
                db_data_14 = cursor.getInt(36);
                db_data_15 = cursor.getInt(37);
                db_data_16 = cursor.getInt(38);
                db_data_17 = cursor.getInt(39);
                db_data_18 = cursor.getInt(40);
                db_data_19 = cursor.getInt(41);
                db_data_20 = cursor.getInt(42);
                db_data_21 = cursor.getInt(43);
                db_data_22 = cursor.getInt(44);
                db_data_23 = cursor.getInt(45);
                db_data_24 = cursor.getInt(46);
                db_data_25 = cursor.getInt(47);
                db_data_26 = cursor.getInt(48);
                db_data_27 = cursor.getInt(49);
                db_data_28 = cursor.getInt(50);
                db_data_29 = cursor.getInt(51);
                db_data_30 = cursor.getInt(52);
                db_data_31 = cursor.getInt(53);
                db_data_32 = cursor.getInt(54);
                db_data_33 = cursor.getInt(55);
                db_data_34 = cursor.getInt(56);
                db_data_35 = cursor.getInt(57);
                db_data_36 = cursor.getInt(58);
                db_data_37 = cursor.getInt(59);
                db_data_38 = cursor.getInt(60);
                db_data_39 = cursor.getInt(61);
                db_data_40 = cursor.getInt(62);
                db_data_41 = cursor.getInt(63);
                db_data_42 = cursor.getInt(64);
                db_data_43 = cursor.getInt(65);
                db_data_44 = cursor.getInt(66);
                db_data_45 = cursor.getInt(67);
                db_data_46 = cursor.getInt(68);
                db_data_47 = cursor.getInt(69);
                db_data_48 = cursor.getInt(70);
                db_data_49 = cursor.getInt(71);
                db_data_50 = cursor.getInt(72);
                db_data_51 = cursor.getInt(73);
                db_data_52 = cursor.getInt(74);
                db_data_53 = cursor.getInt(75);
                db_data_54 = cursor.getInt(76);
                db_data_55 = cursor.getInt(77);
                db_data_56 = cursor.getInt(78);
                db_data_57 = cursor.getInt(79);
                db_data_58 = cursor.getInt(80);
                db_data_59 = cursor.getInt(81);
                db_data_60 = cursor.getInt(82);
                db_data_61 = cursor.getInt(83);
                db_data_62 = cursor.getInt(84);
                db_data_63 = cursor.getInt(85);
                db_data_64 = cursor.getInt(86);
                db_data_65 = cursor.getInt(87);
                db_data_66 = cursor.getInt(88);
                db_data_67 = cursor.getInt(89);
                db_data_68 = cursor.getInt(90);
                db_data_69 = cursor.getInt(91);
                db_data_70 = cursor.getInt(92);
                db_data_71 = cursor.getInt(93);
                db_data_72 = cursor.getInt(94);
                db_data_73 = cursor.getInt(95);
                db_data_74 = cursor.getInt(96);
                db_data_75 = cursor.getInt(97);
                db_data_76 = cursor.getInt(98);
                db_data_77 = cursor.getInt(99);
                db_data_78 = cursor.getInt(100);
                db_data_79 = cursor.getInt(101);
                db_data_80 = cursor.getInt(102);
                db_data_81 = cursor.getInt(103);
                db_data_82 = cursor.getInt(104);
                db_data_83 = cursor.getInt(105);
                db_data_84 = cursor.getInt(106);
                db_data_85 = cursor.getInt(107);
                db_data_86 = cursor.getInt(108);
                db_data_87 = cursor.getInt(109);
                db_data_88 = cursor.getInt(110);
                db_data_89 = cursor.getInt(111);
                db_data_90 = cursor.getInt(112);
                db_data_91 = cursor.getInt(113);
                db_data_92 = cursor.getInt(114);
                db_data_93 = cursor.getInt(115);
                db_data_94 = cursor.getInt(116);
                db_data_95 = cursor.getInt(117);
                db_data_96 = cursor.getInt(118);
                db_data_97 = cursor.getInt(119);
                db_data_98 = cursor.getInt(120);
                db_data_99 = cursor.getInt(121);
                db_data_100 = cursor.getInt(122);
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
            insertValues.put("story", 0);
            insertValues.put("data_1", 0);
            insertValues.put("data_2", 0);
            insertValues.put("data_3", 0);
            insertValues.put("data_4", 0);
            insertValues.put("data_5", 0);
            insertValues.put("data_6", 0);
            insertValues.put("data_7", 0);
            insertValues.put("data_8", 0);
            insertValues.put("data_9", 0);
            insertValues.put("data_10", 0);
            insertValues.put("data_11", 0);
            insertValues.put("data_12", 0);
            insertValues.put("data_13", 0);
            insertValues.put("data_14", 0);
            insertValues.put("data_15", 0);
            insertValues.put("data_16", 0);
            insertValues.put("data_17", 0);
            insertValues.put("data_18", 0);
            insertValues.put("data_19", 0);
            insertValues.put("data_20", 0);
            insertValues.put("data_21", 0);
            insertValues.put("data_22", 0);
            insertValues.put("data_23", 0);
            insertValues.put("data_24", 0);
            insertValues.put("data_25", 0);
            insertValues.put("data_26", 0);
            insertValues.put("data_27", 0);
            insertValues.put("data_28", 0);
            insertValues.put("data_29", 0);
            insertValues.put("data_30", 0);
            insertValues.put("data_31", 0);
            insertValues.put("data_32", 0);
            insertValues.put("data_33", 0);
            insertValues.put("data_34", 0);
            insertValues.put("data_35", 0);
            insertValues.put("data_36", 0);
            insertValues.put("data_37", 0);
            insertValues.put("data_38", 0);
            insertValues.put("data_39", 0);
            insertValues.put("data_40", 0);
            insertValues.put("data_41", 0);
            insertValues.put("data_42", 0);
            insertValues.put("data_43", 0);
            insertValues.put("data_44", 0);
            insertValues.put("data_45", 0);
            insertValues.put("data_46", 0);
            insertValues.put("data_47", 0);
            insertValues.put("data_48", 0);
            insertValues.put("data_49", 0);
            insertValues.put("data_50", 0);
            insertValues.put("data_51", 0);
            insertValues.put("data_52", 0);
            insertValues.put("data_53", 0);
            insertValues.put("data_54", 0);
            insertValues.put("data_55", 0);
            insertValues.put("data_56", 0);
            insertValues.put("data_57", 0);
            insertValues.put("data_58", 0);
            insertValues.put("data_59", 0);
            insertValues.put("data_60", 0);
            insertValues.put("data_61", 0);
            insertValues.put("data_62", 0);
            insertValues.put("data_63", 0);
            insertValues.put("data_64", 0);
            insertValues.put("data_65", 0);
            insertValues.put("data_66", 0);
            insertValues.put("data_67", 0);
            insertValues.put("data_68", 0);
            insertValues.put("data_69", 0);
            insertValues.put("data_70", 0);
            insertValues.put("data_71", 0);
            insertValues.put("data_72", 0);
            insertValues.put("data_73", 0);
            insertValues.put("data_74", 0);
            insertValues.put("data_75", 0);
            insertValues.put("data_76", 0);
            insertValues.put("data_77", 0);
            insertValues.put("data_78", 0);
            insertValues.put("data_79", 0);
            insertValues.put("data_80", 0);
            insertValues.put("data_81", 0);
            insertValues.put("data_82", 0);
            insertValues.put("data_83", 0);
            insertValues.put("data_84", 0);
            insertValues.put("data_85", 0);
            insertValues.put("data_86", 0);
            insertValues.put("data_87", 0);
            insertValues.put("data_88", 0);
            insertValues.put("data_89", 0);
            insertValues.put("data_90", 0);
            insertValues.put("data_91", 0);
            insertValues.put("data_92", 0);
            insertValues.put("data_93", 0);
            insertValues.put("data_94", 0);
            insertValues.put("data_95", 0);
            insertValues.put("data_96", 0);
            insertValues.put("data_97", 0);
            insertValues.put("data_98", 0);
            insertValues.put("data_99", 0);
            insertValues.put("data_100", 0);
            try {
                ret = db.insert("appinfo2", null, insertValues);
            } finally {
                db.close();
            }
            db_isopen = 1;
            db_level = 1;
            db_stamina = 100;
            db_story = 0;

            if (ret == -1) {
                Toast.makeText(this, "DataBase Create.... ERROR", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "DataBase Create.... OK", Toast.LENGTH_SHORT).show();
            }

        } else {

            Toast.makeText(this, "Data Loading...  story:" + db_story, Toast.LENGTH_SHORT).show();

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
        insertValues.put("story", db_story);
        insertValues.put("data_1", db_data_1);
        insertValues.put("data_2", db_data_2);
        insertValues.put("data_3", db_data_3);
        insertValues.put("data_4", db_data_4);
        insertValues.put("data_5", db_data_5);
        insertValues.put("data_6", db_data_6);
        insertValues.put("data_7", db_data_7);
        insertValues.put("data_8", db_data_8);
        insertValues.put("data_9", db_data_9);
        insertValues.put("data_10", db_data_10);
        insertValues.put("data_11", db_data_11);
        insertValues.put("data_12", db_data_12);
        insertValues.put("data_13", db_data_13);
        insertValues.put("data_14", db_data_14);
        insertValues.put("data_15", db_data_15);
        insertValues.put("data_16", db_data_16);
        insertValues.put("data_17", db_data_17);
        insertValues.put("data_18", db_data_18);
        insertValues.put("data_19", db_data_19);
        insertValues.put("data_20", db_data_20);
        insertValues.put("data_21", db_data_21);
        insertValues.put("data_22", db_data_22);
        insertValues.put("data_23", db_data_23);
        insertValues.put("data_24", db_data_24);
        insertValues.put("data_25", db_data_25);
        insertValues.put("data_26", db_data_26);
        insertValues.put("data_27", db_data_27);
        insertValues.put("data_28", db_data_28);
        insertValues.put("data_29", db_data_29);
        insertValues.put("data_30", db_data_30);
        insertValues.put("data_31", db_data_31);
        insertValues.put("data_32", db_data_32);
        insertValues.put("data_33", db_data_33);
        insertValues.put("data_34", db_data_34);
        insertValues.put("data_35", db_data_35);
        insertValues.put("data_36", db_data_36);
        insertValues.put("data_37", db_data_37);
        insertValues.put("data_38", db_data_38);
        insertValues.put("data_39", db_data_39);
        insertValues.put("data_40", db_data_40);
        insertValues.put("data_41", db_data_41);
        insertValues.put("data_42", db_data_42);
        insertValues.put("data_43", db_data_43);
        insertValues.put("data_44", db_data_44);
        insertValues.put("data_45", db_data_45);
        insertValues.put("data_46", db_data_46);
        insertValues.put("data_47", db_data_47);
        insertValues.put("data_48", db_data_48);
        insertValues.put("data_49", db_data_49);
        insertValues.put("data_50", db_data_50);
        insertValues.put("data_51", db_data_51);
        insertValues.put("data_52", db_data_52);
        insertValues.put("data_53", db_data_53);
        insertValues.put("data_54", db_data_54);
        insertValues.put("data_55", db_data_55);
        insertValues.put("data_56", db_data_56);
        insertValues.put("data_57", db_data_57);
        insertValues.put("data_58", db_data_58);
        insertValues.put("data_59", db_data_59);
        insertValues.put("data_60", db_data_60);
        insertValues.put("data_61", db_data_61);
        insertValues.put("data_62", db_data_62);
        insertValues.put("data_63", db_data_63);
        insertValues.put("data_64", db_data_64);
        insertValues.put("data_65", db_data_65);
        insertValues.put("data_66", db_data_66);
        insertValues.put("data_67", db_data_67);
        insertValues.put("data_68", db_data_68);
        insertValues.put("data_69", db_data_69);
        insertValues.put("data_70", db_data_70);
        insertValues.put("data_71", db_data_71);
        insertValues.put("data_72", db_data_72);
        insertValues.put("data_73", db_data_73);
        insertValues.put("data_74", db_data_74);
        insertValues.put("data_75", db_data_75);
        insertValues.put("data_76", db_data_76);
        insertValues.put("data_77", db_data_77);
        insertValues.put("data_78", db_data_78);
        insertValues.put("data_79", db_data_79);
        insertValues.put("data_80", db_data_80);
        insertValues.put("data_81", db_data_81);
        insertValues.put("data_82", db_data_82);
        insertValues.put("data_83", db_data_83);
        insertValues.put("data_84", db_data_84);
        insertValues.put("data_85", db_data_85);
        insertValues.put("data_86", db_data_86);
        insertValues.put("data_87", db_data_87);
        insertValues.put("data_88", db_data_88);
        insertValues.put("data_89", db_data_89);
        insertValues.put("data_90", db_data_90);
        insertValues.put("data_91", db_data_91);
        insertValues.put("data_92", db_data_92);
        insertValues.put("data_93", db_data_93);
        insertValues.put("data_94", db_data_94);
        insertValues.put("data_95", db_data_95);
        insertValues.put("data_96", db_data_96);
        insertValues.put("data_97", db_data_97);
        insertValues.put("data_98", db_data_98);
        insertValues.put("data_99", db_data_99);
        insertValues.put("data_100", db_data_100);

        int ret;
        try {
            ret = db.update("appinfo2", insertValues, null, null);
        } finally {
            db.close();
        }

        if (ret == -1) {
            Toast.makeText(this, "Saving.... ERROR ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Saving.... OK "+ "op=0:"+db_isopen+" story=:"+db_story+" data=61:"+db_data_61, Toast.LENGTH_SHORT).show();
        }

    }

}