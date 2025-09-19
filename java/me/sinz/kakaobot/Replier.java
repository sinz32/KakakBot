package me.sinz.kakaobot;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Replier {
    private final Notification.Action[] actions;
    private final String packageName;
    private final long userId;
    private final Context ctx;

    public Replier(Context ctx, Notification.Action[] actions, String packageName, long userId, boolean isDebug) {
        this.actions = actions;
        this.packageName = packageName;
        this.userId = userId;
        this.ctx = ctx;
    }

    public void reply(String msg) {
        for (Notification.Action action : actions) {
            if (action.getRemoteInputs() == null) continue;
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            for (RemoteInput inputable : action.getRemoteInputs()) {
                bundle.putCharSequence(inputable.getResultKey(), msg);
            }
            RemoteInput.addResultsToIntent(action.getRemoteInputs(), intent, bundle);
            try {
                action.actionIntent.send(ctx, 0, intent);
            } catch (PendingIntent.CanceledException e) {}
        }
    }

    public void markAsRead() {
        for (Notification.Action action : actions) {
            try {
                action.actionIntent.send(ctx, 1, new Intent());
            } catch (PendingIntent.CanceledException e) {}
        }
    }

}
