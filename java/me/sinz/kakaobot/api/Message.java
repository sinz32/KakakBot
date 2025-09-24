package me.sinz.kakaobot.api;

import org.mozilla.javascript.ScriptableObject;

import me.sinz.kakaobot.Replier;

public class Message extends ScriptableObject {

    private ScriptableObject scope;

    private String room, content, packageName, channelId, logId;
    private Author author;
    private boolean isGroupChat;
    private Replier replier;
    private Image image;
    private int userId;

    public Message(ScriptableObject scope, String room, String msg, Author author, boolean isGroupChat, Replier replier,
                   Image ImageDB, String packageName, int userId, String chatId, String chatLogId) {
        this.scope = scope;
        this.room = room;
        this.content = msg;
        this.author = author;
        this.isGroupChat = isGroupChat;
        this.image = new Image(scope);
        this.packageName = packageName;
        this.userId = userId;
        this.channelId = chatId;
        this.logId = chatLogId;
        this.replier = replier;

        initParameters();
    }

    private void initParameters() {
        putConst("room", this, this.room);
        putConst("content", this, this.content);
        putConst("author", this, this.author);
        putConst("isGroupChat", this, this.isGroupChat);
        putConst("image", this, this.image);
        putConst("packageName", this, this.packageName);
        putConst("channelId", this, this.channelId);
        putConst("logId", this, this.logId);
        putConst("isMultiChat", this, this.userId != 0);

        putConst("isDebugRoom", this, false);


        defineFunctionProperties(new String[]{
                "reply",
                "markAsRead"
        }, Message.class, 0);
    }

    public void reply(String msg) {
        this.replier.reply(msg);
    }

    public void markAsRead() {
        this.replier.markAsRead();
    }

    @Override
    public String getClassName() {
        return "Message";
    }

}
