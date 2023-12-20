package tfsapps.treasurehunt;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

public class CustomDialog {

    /* 一時ポップアップ表示 */
    public static void showCustomDialog(Context context, @DrawableRes int imageResId, String message, int step) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // カスタムレイアウトを設定
        View view = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null);
        builder.setView(view);
        // レイアウト内のViewを取得
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(imageResId);
        // メッセージを設定
        TextView textView = view.findViewById(R.id.textView);
        textView.setText(message);
        textView.setTextSize(16);
        textView.setGravity(Gravity.CENTER);
        // ダイアログを表示
//        builder.setPositiveButton("OK", null); // ボタンのリスナーを設定する場合はここに追加
        builder.setPositiveButton( "次へ" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (step == 1) {            //スコップ処理
                    if (context instanceof MainActivity) {
                        ((MainActivity) context).ScoopResult();
                    }
                }
                else if (step == 11){       //福引券処理
                    if (context instanceof MainActivity) {
                        ((MainActivity) context).LotDone();
                    }
                }
                else if (step == 21){       //スカウトベル処理
                    if (context instanceof MainActivity) {
                        ((MainActivity) context).ScoutDone();
                    }
                }
                else if (step == 12){       //福引結果
                    if (context instanceof MainActivity) {
                        ((MainActivity) context).LotResult();
                    }
                }
                else if (step == 22){       //スカウト結果
                    if (context instanceof MainActivity) {
                        ((MainActivity) context).ScoutResult();
                    }
                }
                return;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    /* 一時ポップアップ表示(YES/NO) */
    public static void showCustomDialogYesNo(Context context, @DrawableRes int imageResId, String message, int YesStep, int NoStep) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // カスタムレイアウトを設定
        View view = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null);
        builder.setView(view);
        // レイアウト内のViewを取得
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(imageResId);
        // メッセージを設定
        TextView textView = view.findViewById(R.id.textView);
        textView.setText(message);
        textView.setTextSize(13);
        textView.setGravity(Gravity.TOP | Gravity.LEFT);
        // ダイアログを表示
//        builder.setPositiveButton("OK", null); // ボタンのリスナーを設定する場合はここに追加
        builder.setPositiveButton( "はい" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (YesStep == 1) {            //スコップ処理
                    if (context instanceof MainActivity) {
                        ((MainActivity) context).StaminaRecovery();
                    }
                }
                if (YesStep == 2) {            //スコップ処理
                    if (context instanceof MainActivity) {
                        ((MainActivity) context).GameEndDone();
                    }
                }
                return;
            }
        });
        builder.setNegativeButton( "いいえ" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*
                if (step == 1) {            //スコップ処理
                    if (context instanceof MainActivity) {
                        ((MainActivity) context).ScoopResult();
                    }
                }

                 */
                return;
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /* ストーリー専用 */
    public static void showCustomDialogStory(Context context, @DrawableRes int imageResId, String message, int gravity, int step) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // カスタムレイアウトを設定
        View view = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null);
        builder.setView(view);

        // レイアウト内のViewを取得
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(imageResId);

        // メッセージを設定
        TextView textView = view.findViewById(R.id.textView);
        textView.setText(message);
        textView.setTextSize(13);
        textView.setGravity(Gravity.TOP | Gravity.LEFT);

        // ダイアログを表示
//        builder.setPositiveButton("OK", null); // ボタンのリスナーを設定する場合はここに追加
        builder.setPositiveButton( "次へ" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (step == 1) {
                    if (context instanceof MainActivity) {
                        ((MainActivity) context).MyStory();
                    }
                }
                return;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
