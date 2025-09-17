package me.sinz.kakaobot.api;

import org.mozilla.javascript.ScriptableObject;

public class Author extends ScriptableObject {
    public Image avatar;
    public String hash;
    public String name;

    public Author(ScriptableObject scope, String name, Image avatar, String hash) {
        super(scope, scope.getPrototype());
        this.name = name;
        this.avatar = avatar;
        this.hash = hash;
        putConst("name", this, name);
        putConst("avatar", this, avatar);
        putConst("hash", this, hash);
    }

    @Override
    public String getClassName() {
        return "Author";
    }

    @Override
    public String toString() {
        return "{" +
                "name:\"" + name + "\"," +
                "avatar:" + avatar.toString() + "," +
                "hash:\"" + hash + "\"" +
                "}";
    }
}
