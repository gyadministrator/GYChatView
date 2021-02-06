package com.android.gychatview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.chatlib.activity.ChatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnChat;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        initView();
        initEvent();
    }

    private void initEvent() {
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatActivity.startActivity(mActivity, "李四");
            }
        });
    }

    private void initView() {
        btnChat = findViewById(R.id.btn_chat);
    }
}
