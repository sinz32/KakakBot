package me.sinz.kakaobot.api;

import android.content.Context;

import org.mozilla.javascript.Function;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;

import me.sinz.kakaobot.KakaoTalkListener;

public class App extends ScriptableObject {
    private final String botName;

    public App(ScriptableObject scope, String botName) {
        super(scope, scope.getPrototype());
        this.botName = botName;
        defineFunctionProperties(new String[] { "getContext", "runOnUiThread" }, App.class, 0);
    }

    @Override
    public String getClassName() {
        return "App";
    }
}