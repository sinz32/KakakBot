package me.sinz.kakaobot.api;

import android.graphics.Bitmap;

import org.mozilla.javascript.ScriptableObject;

import me.sinz.kakaobot.SinZ;

public class Image extends ScriptableObject {
    public final Bitmap bitmap;

    public Image(ScriptableObject scope) {
        this(scope, null);
    }

    public Image(ScriptableObject scope, Bitmap bitmap) {
        super(scope, scope.getPrototype());
        this.bitmap = bitmap;
        putConst("bitmap", this, bitmap);
        putConst("base64", this, SinZ.encodeImage(bitmap));
        defineFunctionProperties(new String[]{"getBase64", "getBitmap"}, Image.class, 0);
    }

    public String getBase64() {
        return SinZ.encodeImage(bitmap);
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public String getClassName() {
        return "Image";
    }

    @Override
    public String toString() {
        return "{getBase64: function(){},getBitmap: function(){}}";
    }
}