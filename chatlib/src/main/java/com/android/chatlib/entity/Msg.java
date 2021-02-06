package com.android.chatlib.entity;

/**
 * @ProjectName: GYChatView
 * @Package: com.android.chatlib.entity
 * @ClassName: Msg
 * @Author: 1984629668@qq.com
 * @CreateDate: 2021/2/6 15:24
 */
public class Msg {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;
    private String content;//信息内容
    private int type;//信息种类，是接受的信息或发出的信息

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
