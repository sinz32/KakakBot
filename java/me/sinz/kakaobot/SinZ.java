package me.sinz.kakaobot;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Base64;
import android.view.Gravity;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class SinZ {

    private static final String PREFERENCES_NAME = "bot_settings";
    public static String TAG = "sinz";

    public static void saveSettings(Context ctx, String name, boolean value) {
        SharedPreferences sp = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(name, value);
        editor.apply();
    }

    public static boolean loadSettings(Context ctx, String name, boolean defaultSettings) {
        SharedPreferences sp = ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(name, defaultSettings);
    }

    public static int dip2px(Context ctx, int dips) {
        return (int) Math.ceil(dips * ctx.getResources().getDisplayMetrics().density);
    }

    public static TextView copyright(Context ctx) {
        TextView txt = new TextView(ctx);
        txt.setText("© 2021-2025 SinZ, All rights reserved.");
        txt.setGravity(Gravity.CENTER);
        txt.setTextSize(12);
        int pad = dip2px(ctx, 8);
        txt.setPadding(pad, pad, pad, pad);
        return txt;
    }

    public static Notification.Builder createNotifation(Context ctx, String channel, String name) {
        if (Build.VERSION.SDK_INT < 26) return new Notification.Builder(ctx);
        NotificationChannel nc = new NotificationChannel(channel, name, NotificationManager.IMPORTANCE_LOW);
        NotificationManager nm = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        if (nm != null) nm.createNotificationChannel(nc);
        return new Notification.Builder(ctx, channel);
    }

    public static String encodeImage(Bitmap bitmap) {
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return Base64.encodeToString(baos.toByteArray(), 0).trim();
    }

}
