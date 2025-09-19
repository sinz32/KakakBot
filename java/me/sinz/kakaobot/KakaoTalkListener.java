package me.sinz.kakaobot;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

import me.sinz.kakaobot.api.Event;

public class KakaoTalkListener extends NotificationListenerService {

    public static Context ctx;
    private static Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        ctx = getApplicationContext();

        if (SinZ.loadSettings(this, "bot_onoff", false)) {
            startForeground();
        }
    }

    private void startForeground() {
        try {
            Notification.Builder noti = SinZ.createNotifation(this, "kakao_bot_main", "Kakao Bot");
            noti.setSmallIcon(R.mipmap.ic_launcher);
            noti.setContentTitle("카카오톡 봇");
            noti.setContentText("카카오톡 봇 실행 중...");
            noti.setOngoing(false);
            noti.setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0));
            startForeground(1, noti.build());
        } catch (Exception e) {
            toast("포그라운드에서 실행 실패\n" + e.toString());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    public static void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }


    @Override
    public void onNotificationPosted(final StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        if (!SinZ.loadSettings(this, "bot_onoff", false)) return;
        ScriptManager.callScriptMethod(Event.NOTIFICATION_POSTED, new Object[]{sbn});

        if (!sbn.getPackageName().equals("com.kakao.talk")) return;
        try {
            //최근 방식으로 Action 목록 추출
            Notification.Action[] actions = sbn.getNotification().actions;

            //결과물이 없다면, 옛날 방식으로 Action 목록 추출
            if (actions == null) {
                Notification.WearableExtender wExt = new Notification.WearableExtender(sbn.getNotification());
                actions = wExt.getActions().toArray(new Notification.Action[0]);
            }

            //애초에 응답 전송이 불가능한 알림이라면 중단
            if (actions.length == 0) return;

            Bundle bundle = sbn.getNotification().extras;
            String msg = bundle.get("android.text").toString(); //String이 아닐 수도 있음 (카톡 구버전 + 채팅에 멘션 포함, isMention 구현 안할 예정)
            String sender = bundle.getString("android.title");
            String room = bundle.getString("android.subText"); //알림 구조 변경 대응
            if (room == null) bundle.getString("android.summaryText");


        } catch (Exception e) {
            toast("onNoti, " + e.toString());
        }


    }


    public static void toast(final String msg) {
        KakaoTalkListener.runOnUiThread(() -> Toast.makeText(KakaoTalkListener.ctx, msg, Toast.LENGTH_SHORT).show());
    }

}
