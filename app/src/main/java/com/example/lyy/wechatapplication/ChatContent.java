package com.example.lyy.wechatapplication;

public class ChatContent {
    private boolean me;
    private String Content;

    public boolean isMe() {
        return me;
    }

    public void setMe(boolean me) {
        this.me = me;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
