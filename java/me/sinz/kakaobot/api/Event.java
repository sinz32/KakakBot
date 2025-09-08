package me.sinz.kakaobot.api;

import android.util.Log;

import org.mozilla.javascript.ScriptableObject;

import java.lang.reflect.Field;

import me.sinz.kakaobot.SinZ;

public class Event extends ScriptableObject {
    public static String MESSAGE = "message";
    public static String COMMAND = "command";
    public static String NOTIFICATION_POSTED = "notification_posted";
    public static String START_COMPILE = "start_compile";
    public static String TICK = "tick";

    public Event(ScriptableObject so) {
        super(so, so.getPrototype());
        try {
            for (Field field : Event.class.getDeclaredFields()) {
                putConst(field.getName(), this, field.get(null));
            }
        } catch (IllegalAccessException e) {
            Log.i(SinZ.TAG, "Event, " + e.toString());
        }
    }

    public String getClassName() {
        return "Event";
    }

}
