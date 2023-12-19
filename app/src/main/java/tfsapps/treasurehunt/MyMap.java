package tfsapps.treasurehunt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//test_make
//public class MyMap extends View {
public class MyMap extends SurfaceView implements SurfaceHolder.Callback {

    final int START_X1 = 10;
    final int START_Y1 = 10;
    final int TATE = 50;
    final int YOKO = 50;
    private int MASU = 8;
    final float WIDTH = 5.0f;
    public Paint paint;
    public Canvas canvas;
    private static final long FPS = 60;
    private final Random rand = new Random(System.currentTimeMillis());

    private int Hankei = 100;   //半径
    private int Max_X;
    private int Max_Y;

    final double IDO_1M = 0.000008983148616;
    final double KEIDO_1M = 0.000010966382364;
    final double IDO_20M = (0.000008983148616 * 20.0f);
    final double KEIDO_20M = (0.000010966382364 * 20.0f);

    final double IDO_100M = (0.000008983148616 * 100.0f);
    final double KEIDO_100M = (0.000010966382364 * 100.0f);

    final double IDO_200M = (0.000008983148616 * 200.0f);
    final double KEIDO_200M = (0.000010966382364 * 200.0f);

    final double IDO_500M = (0.000008983148616 * 500.0f);
    final double KEIDO_500M = (0.000010966382364 * 500.0f);

    final double IDO_1000M = (0.000008983148616 * 1000.0f);
    final double KEIDO_1000M = (0.000010966382364 * 1000.0f);

    public double init_pos_ido = 0.0f;
    public double init_pos_kei = 0.0f;
    private double ido_1 = 0.0f;
    private double ido_2 = 0.0f;
    private double kei_1 = 0.0f;
    private double kei_2 = 0.0f;
    private double now_ido = 0.0f;
    private double now_kei = 0.0f;
    private double bak_ido = 0.0f;
    private double bak_kei = 0.0f;

    long _refresh = 0;   //画面リフレッシュ　履歴表示クリア
    long _blink = 0;     //お宝ヒット中の表示

    /*
    static public double init_pos_ido = 0.0f;
    static public double init_pos_kei = 0.0f;
    static private double ido_1 = 0.0f;
    static private double ido_2 = 0.0f;
    static private double kei_1 = 0.0f;
    static private double kei_2 = 0.0f;

    static private double now_ido = 0.0f;
    static private double now_kei = 0.0f;
    static private double bak_ido = 0.0f;
    static private double bak_kei = 0.0f;

    static long _refresh = 0;   //画面リフレッシュ　履歴表示クリア
    static long _blink = 0;     //お宝ヒット中の表示
    */
    private final List<Treasure> treasuresList = new ArrayList<Treasure>();
    public Treasure nowTreasure;


    //        public MyMap(Context context) {
//        super(context);
//        setWillNotDraw(false);
//        public MyMap(Context context, AttributeSet attrs){
//        super(context, attrs);
    public MyMap(Context context){
        super(context);
        getHolder().addCallback(this);
        paint = new Paint();
    }

    protected void drawObject(Canvas canvas) {
//    @Override
//    protected void onDraw(Canvas canvas) {
        int i ,j;
        int rader = 0; //レーダーの範囲

        // ペイントする色の設定
        if (this.isHitTreasure() ){
            _blink++;
            if (_blink < 50 ){
                paint.setColor(Color.argb(255, 100, 255, 100));
            }
            else{
                paint.setColor(Color.argb(255, 100, 190, 100));
            }
            if (_blink > 100)   _blink = 0;
        }
        else {
            paint.setColor(Color.argb(255, 100, 255, 100));
        }
        // ペイントストロークの太さを設定
        paint.setStrokeWidth(WIDTH);
        // Styleのストロークを設定する
        paint.setStyle(Paint.Style.STROKE);
        Max_X = (int)getWidth();
        Max_Y = (int)getHeight();
        MASU = ((Max_X * 10 / 10) / YOKO);

        int temp_x1 = START_X1;
        int temp_y1 = START_Y1;
        int temp_x2 = START_X1 + YOKO;
        int temp_y2 = START_Y1 + TATE;

        //再描画表示
        _refresh++;
        if (_refresh > 48) {
            _refresh = 0;
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        }

        // drawRectを使って矩形を描画する、引数に座標を設定
        // (x1,y1,x2,y2,paint) 左上の座標(x1,y1), 右下の座標(x2,y2)

        for(j=0; j<MASU; j++) {
            // MAP　横の描画
            for (i = 0; i < MASU; i++) {
                canvas.drawRect(temp_x1 + (i * YOKO), temp_y1+(j*TATE),
                        temp_x2 + (i * YOKO), temp_y2+(j*TATE), paint);
            }
        }

        float dp = 5.0f;
        float xc = (getWidth() / 2);
        float yc = xc;
        float xc_n = 0, yc_n = 0;

        //初期の位置
        if (init_pos_ido == 0.0f || init_pos_kei == 0.0f){
            // 何も表示しない
        }
        else {
            //自身の位置(初期値)
            paint.setColor(Color.argb(255, 255, 125, 125));
            paint.setStrokeWidth(15.0f);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            // (x1,y1,r,paint) 中心x1座標, 中心y1座標, r半径
            canvas.drawCircle(xc - dp, yc - dp, 10, paint);
        }

        //レーダー範囲
        rader = 200;
//      rader = (Max_X / MASU);   // 100m ÷　MASU（1080dot の時　MASU=21）

        //自身の位置(現在の位置)
        if (this.now_kei == 0.0f || this.now_ido == 0.0f) {
            //表示せず
        }else{
            /* 自身の位置 */
            paint.setColor(Color.argb(255, 125, 125, 255));
            paint.setStrokeWidth(15.0f);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            xc = (float) (this.now_kei * getWidth());
            yc = (float) (this.now_ido * getWidth());

            // (x1,y1,r,paint) 中心x1座標, 中心y1座標, r半径
            canvas.drawCircle(xc - dp, yc - dp, 15, paint);

            /* レーダーの範囲 */
            paint.setColor(Color.argb(255, 125, 225, 255));
            paint.setStrokeWidth(5.0f);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            xc = (float) (this.now_kei * getWidth());
            yc = (float) (this.now_ido * getWidth());

            xc_n = xc;
            yc_n = yc;

            // (x1,y1,r,paint) 中心x1座標, 中心y1座標, r半径
            canvas.drawCircle(xc - dp, yc - dp, rader, paint);
        }

        /*
        //自身の位置(一つ前の位置　履歴表示)
        if (this.bak_kei == 0.0f || this.bak_ido == 0.0f) {
            //表示せず
        }else {
            paint.setColor(Color.argb(255, 200, 200, 200));
            paint.setStrokeWidth(12.0f);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            xc = (float) (this.bak_kei * getWidth());
            yc = (float) (this.bak_ido * getWidth());

            // (x1,y1,r,paint) 中心x1座標, 中心y1座標, r半径
            canvas.drawCircle(xc - dp, yc - dp, 14, paint);
        }
        */

        float xc2;
        float yc2;

        float range = 200;

        //宝物を表示
        if (init_pos_ido == 0.0f || init_pos_kei == 0.0f){
            // 何も表示しない
        }
        else {
            for (int k=0; k < treasuresList.size(); k++) {

                //ザクザクしたものは表示しない
                if (treasuresList.get(k).isAlive == false) {
                    continue;
                }

                //自身の位置(初期値)
                paint.setColor(Color.argb(255, 255, 255, 125));
                paint.setStrokeWidth(15.0f);
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.STROKE);
                xc2 = (float) (treasuresList.get(k).t_pos_x );
                yc2 = (float) (treasuresList.get(k).t_pos_y );

                //ヒット状態をリセット
                treasuresList.get(k).isHit = false;

                /* 自身の位置が　★宝の近くにある状態 */
                if (xc_n != 0.0f && yc_n != 0.0f){
                    if ( (xc_n - range) <= xc2 && xc2 <= (xc_n + range) ){
                        if ( (yc_n - range) <= yc2 && yc2 <= (yc_n + range) ){
                            treasuresList.get(k).isHit = true;
                            paint.setColor(Color.argb(255, 0, 255, 0));
                        }
                    }
                }

                // (x1,y1,r,paint) 中心x1座標, 中心y1座標, r半径
                canvas.drawCircle( xc2 - dp, yc2 - dp, 10, paint);
//                canvas.drawCircle(treasuresList.get(k).t_pos_x - dp, treasuresList.get(k).t_pos_y - dp, 10, paint);
//                canvas.drawCircle( xc - dp, yc - dp, 10, paint);

            }
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
        お宝確率
        40% お宝　直値で下記は激レア
            01:王冠　　激レア
            02:指輪　　激レア
            03:絵画　　激レア

        　4〜40の間は、以下のパターン
            11:コイン  〜11
            12:ふくびき券
            13:スカウトベル

        60% ガラクタ　以下の５パターン
            61:やかん
            62:かさ
            63:なべ
            64:新聞紙
            65:乾電池
    ****************************************************/
    public int TreasureSelect() {
        int type_1 = 0;
        int type_2 = 0;
        int type = 0;
        type_1 = rand.nextInt(100);

        //41~99  60%は「ガラクタ」
        if (type_1 > 40){
            type_2 = rand.nextInt(5);
            switch (type_2){
                case 0: type = 61; break;
                case 1: type = 62; break;
                case 2: type = 63; break;
                case 3: type = 64; break;
                case 4: type = 65; break;
            }
        }
        //0~40   40%は「宝」
        else{
            if (type_1 == 1){
                type = 1;
            }
            else if (type_1 == 2){
                type = 2;
            }
            else if (type_1 == 3){
                type = 3;
            }
            else {
                type_2 = rand.nextInt(40);
                if (type_2 < 10){
                    type = 11;
                }
                else if (type_2 >= 11 && type_2 <= 21){
                    type = 12;
                }
                else{
                    type = 13;
                }
            }
        }

        return type;
    }

    // スタート位置
    public void InitialSetting(double i_ido, double i_kei){
        //  自身の位置
        this.init_pos_ido = i_ido;
        this.init_pos_kei = i_kei;

        Max_X = (int)getWidth();
        Max_Y = (int)getHeight();
        MASU = ((Max_X * 10 / 10) / YOKO);

        /*
         1マスは50で、最大1000　縦横同じ
         kei_1,ido2 --------- 100M ---------kei_2
         |
         |
         |
         |
         100M
         |
         |
         |
         ido_1
         */


        //  範囲 100 m
        this.ido_1 = (this.init_pos_ido - IDO_100M);
        this.ido_2 = (this.init_pos_ido + IDO_100M);
        this.kei_1 = (this.init_pos_kei - KEIDO_100M);
        this.kei_2 = (this.init_pos_kei + KEIDO_100M);
/*
        //  範囲 200 m
        this.ido_1 = (this.init_pos_ido - IDO_200M);
        this.ido_2 = (this.init_pos_ido + IDO_200M);
        this.kei_1 = (this.init_pos_kei - KEIDO_200M);
        this.kei_2 = (this.init_pos_kei + KEIDO_200M);

        //  範囲 500 m
        this.ido_1 = (this.init_pos_ido - IDO_500M);
        this.ido_2 = (this.init_pos_ido + IDO_500M);
        this.kei_1 = (this.init_pos_kei - KEIDO_500M);
        this.kei_2 = (this.init_pos_kei + KEIDO_500M);

        //  範囲 1000 m
        this.ido_1 = (this.init_pos_ido - IDO_1000M);
        this.ido_2 = (this.init_pos_ido + IDO_1000M);
        this.kei_1 = (this.init_pos_kei - KEIDO_1000M);
        this.kei_2 = (this.init_pos_kei + KEIDO_1000M);

 */

        //宝物セット
        int _x;
        int _y;
        double _dx;
        double _dy;
        int _type;
        int _x_pm;
        int _y_pm;
        double _ido;
        double _kei;
        int i = 0;
        while ( i<15     ){
            _x = rand.nextInt(YOKO * MASU ); //1000
            _y = rand.nextInt(TATE * MASU ); //1000

            i++;
            /*
            if (_x > YOKO * 7 && _y > TATE * 7){
                i++;
            }
            else {
                continue;
            }*/

            _type  = TreasureSelect();
            _x_pm = rand.nextInt(2);
            _y_pm = rand.nextInt(2);
//            _x += 50;
//            _y += 50;
            _dx = (double)( _x * KEIDO_1M );
            _dy = (double)( _y * IDO_1M );
            _dx = (double)( 100 * KEIDO_1M );

            if (_x_pm > 0) {
                _kei = this.init_pos_kei + _dx;
            }
            else{
                _kei = this.init_pos_kei + (-1 * _dx);

            }
            if (_y_pm > 0) {
                _ido = this.init_pos_ido + _dy;
            }
            else{
                _ido = this.init_pos_ido + ( -1 * _dy);
            }

            Treasure my_treasure = new Treasure(_x, _y, _type, _kei, _ido);
            treasuresList.add(my_treasure);
        }

    }

    // スタート位置
    public void UpdatePosition(double t_ido, double t_kei){
        double x, y, tmp, tmp2;

        bak_ido = now_ido;
        bak_kei = now_kei;
        //  範囲
        tmp = (t_ido - this.ido_1);
        tmp2 = (this.ido_2 - this.ido_1);
        this.now_ido = tmp / tmp2;

        tmp = (t_kei - this.kei_1);
        tmp2 = (this.kei_2 - this.kei_1);
        this.now_kei = tmp / tmp2;
    }

/*test_make*/

    /* 宝物にヒットしているのか？ */
    public boolean isHitTreasure(){
        if (treasuresList.size() == 0){
            nowTreasure = null;
            return false;
        }
        for (int k=0; k < treasuresList.size(); k++) {
            if (treasuresList.get(k).isHit == true) {
                if (treasuresList.get(k).isAlive == false){
                    continue;
                }
                nowTreasure = treasuresList.get(k);
                return true;
            }
        }
        nowTreasure = null;
        return false;
    }
    /* 宝物ザクザクの結果 */
    public int ZakuZakuResult(){
        int ret = -99;
        if (nowTreasure != null){
            ret = nowTreasure.t_type;

            //結果が出たので削除する
            for (int k=0; k < treasuresList.size(); k++) {
                if (treasuresList.get(k).isAlive == false){
                    continue;
                }
                if (nowTreasure.t_kei == treasuresList.get(k).t_kei &&
                        nowTreasure.t_ido == treasuresList.get(k).t_ido
                    ){
                    nowTreasure = null;
                    treasuresList.get(k).isAlive = false;
                    break;
                }
            }
        }
        return ret;
    }

    /********************************************************************************
         ゲーム全体の描画間隔
     *********************************************************************************/
    private class DrawThread extends Thread {
        private boolean isFinished;
        @Override
        public void run() {
            super.run();
            SurfaceHolder holder = getHolder();
            while (!isFinished) {
                Canvas canvas = holder.lockCanvas();
                if (canvas != null) {
                    drawObject(canvas);
                    holder.unlockCanvasAndPost(canvas);
                }

                try {
                    sleep(1000 / FPS);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    private DrawThread drawThread;

    public interface Callback {
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void startDrawThread() {
        stopDrawThread();
        drawThread = new DrawThread();
        drawThread.start();
    }

    public boolean stopDrawThread() {
        if (drawThread == null) {
            return false;
        }
        drawThread.isFinished = true;
        drawThread = null;
        return true;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        startDrawThread();  //描画スレッド起動
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

}
