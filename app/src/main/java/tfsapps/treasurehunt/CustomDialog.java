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
        builder.setPositiveButton( "OK" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (step == 1) {
                    if (context instanceof MainActivity) {
                        ((MainActivity) context).ScoopResult();
                    }
                }
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
        builder.setPositiveButton( "OK" , new DialogInterface.OnClickListener() {
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
