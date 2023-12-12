package tfsapps.treasurehunt;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class MyDialog {

    public AlertDialog.Builder _guide;
    public TextView _text;
    public String _title;
    public String _mess;
    public int _nextstep;
    public Context _context;

    public MyDialog(Context context, String title, String mess, int nextstep){
        _context = context;
        _guide = new AlertDialog.Builder(context);
        _text = new TextView(context);
        _title = "\n" + title + "\n";
        _mess = "\n\n\n" + mess + "\n\n\n";
        _nextstep = nextstep;
    }

    @SuppressLint("ResourceAsColor")
    public void PopShow(){

        _text.setText(_mess);
//        _text.setBackgroundColor( R.color.white );
//        _text.setTextColor( R.color.black );
        _text.setTypeface(Typeface.DEFAULT_BOLD);

        _guide.setTitle(_title);
//        _guide.setIcon(R.drawable.book2);
        _guide.setView(_text);
        _guide.setPositiveButton( "OK" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (_nextstep == 1) {
                    if (_context instanceof MainActivity) {
                        ((MainActivity) _context).ScoopResult();
                    }
                }
                return;
            }
        });
        _guide.setCancelable(false);
        _guide.create();
        _guide.show();
    }

    //メッセージ



    /*
    public static void ShowTransparentDialog(Context context, String title, String message) {
        // ダイアログのレイアウトをインフレート
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.my_dialog, null);

        TextView messageTextView = dialogView.findViewById(R.id.dialog_message);
        messageTextView.setText(message);
        messageTextView.setTextColor(Color.WHITE);

        // ダイアログを作成
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        builder.setTitle(title);
        builder.setMessage(message);

        // ダイアログを作成して表示
        AlertDialog dialog = builder.create();
        dialog.show();

        // ダイアログの背景を半透明に設定
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // ダイアログの閉じるボタンを設定
        builder.setNegativeButton("閉じる", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

     */

}

