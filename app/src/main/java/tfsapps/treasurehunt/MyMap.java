package tfsapps.treasurehunt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

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

    static private double init_pos_ido = 0.0f;
    static private double init_pos_kei = 0.0f;
    static private double ido_1 = 0.0f;
    static private double ido_2 = 0.0f;
    static private double kei_1 = 0.0f;
    static private double kei_2 = 0.0f;

    static private double now_ido = 0.0f;
    static private double now_kei = 0.0f;
    static private double bak_ido = 0.0f;
    static private double bak_kei = 0.0f;


    //        public MyMap(Context context) {
//        super(context);
//        setWillNotDraw(false);
//    public MyMap(Context context, AttributeSet attrs){
//          super(context, attrs);
    public MyMap(Context context){
        super(context);
        getHolder().addCallback(this);
        paint = new Paint();
    }

    protected void drawObject(Canvas canvas) {
//    @Override
//    protected void onDraw(Canvas canvas) {
        int i ,j;

        // ペイントする色の設定
        paint.setColor(Color.argb(255, 100, 255, 100));
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

        //自身の位置(現在の位置)
        if (this.now_kei == 0.0f || this.now_ido == 0.0f) {
            //表示せず
        }else{
            paint.setColor(Color.argb(255, 125, 125, 255));
            paint.setStrokeWidth(15.0f);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            xc = (float) (this.now_kei * getWidth());
            yc = (float) (this.now_ido * getWidth());

            // (x1,y1,r,paint) 中心x1座標, 中心y1座標, r半径
            canvas.drawCircle(xc - dp, yc - dp, 15, paint);
        }
        //自身の位置(現在の位置)
        if (this.bak_kei == 0.0f || this.bak_ido == 0.0f) {
            //表示せず
        }else{
            paint.setColor(Color.argb(255, 200, 200, 200));
            paint.setStrokeWidth(12.0f);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            xc = (float) (this.bak_kei * getWidth());
            yc = (float) (this.bak_ido * getWidth());

            // (x1,y1,r,paint) 中心x1座標, 中心y1座標, r半径
            canvas.drawCircle(xc - dp, yc - dp, 14, paint);
        }

    }

    // スタート位置
    public void InitialSetting(double i_ido, double i_kei){
        //  自身の位置
        this.init_pos_ido = i_ido;
        this.init_pos_kei = i_kei;

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
