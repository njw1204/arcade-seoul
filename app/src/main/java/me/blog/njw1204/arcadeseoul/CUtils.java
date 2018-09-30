package me.blog.njw1204.arcadeseoul;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

public class CUtils {
    public static String SearchableString(String string) {
        return string.replaceAll("\\s", "").toLowerCase();
    }

    public static int DP(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static boolean IsEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    public static void SimpleDialogShow(Context context, String msg, boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setMessage(msg);
        builder.setCancelable(cancelable);
        builder.show();
    }

    public static void SimpleTitleDialogShow(Context context, String title, String msg, boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(cancelable);
        builder.show();
    }

    public static void StartSimpleWebActivity(Context context, String url, String title){
        Intent intent = new Intent(context, SimpleWebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }
}
