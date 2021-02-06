package com.android.chatlib.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.chatlib.R;
import com.android.chatlib.adapter.MsgAdapter;
import com.android.chatlib.entity.Msg;
import com.gyf.immersionbar.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private List<Msg> msgList = new ArrayList<>();
    private Button btnSend;
    private EditText etMessage;
    private Activity mActivity;
    @SuppressLint("StaticFieldLeak")
    private static ChatActivity instance;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    private LinearLayout llBack;
    private TextView tvTitle;
    private String title = "";

    public static void startActivity(Activity activity, String title) {
        if (instance == null) {
            Intent intent = new Intent(activity, ChatActivity.class);
            intent.putExtra("title", title);
            activity.startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ImmersionBar.with(this).barColor("#FFFFFF").init();
        instance = this;
        mActivity = this;
        initView();
        initData();
        initAdapter();
        initEvent();
        etMessage.requestFocus();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent.hasExtra("title")) {
            title = intent.getStringExtra("title");
        }
        tvTitle.setText(title);
    }

    private String getMessage() {
        return etMessage.getText().toString();
    }

    private void initEvent() {
        /**
         * 发送按钮的点击事件
         */
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendMessage();
            }
        });
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    int length = s.length();
                    if (length > 499) {
                        Toast.makeText(mActivity, "最多输入500个字", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendMessage();
                    return true;
                }
                return false;
            }
        });
    }

    private void sendMessage() {
        hintKeyBoard();
        if (TextUtils.isEmpty(getMessage())) {
            Toast.makeText(mActivity, "发送内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Msg msg = new Msg(getMessage(), Msg.TYPE_SENT);
        msgList.add(msg);
        adapter.notifyItemInserted(msgList.size() - 1);//调用适配器的notifyItemInserted()方法,用于通知适配器有新的数据插入
        msgRecyclerView.scrollToPosition(msgList.size() - 1);//将显示的数据定位到最后一行
        etMessage.setText("");
        EventBus.getDefault().post("123");
    }

    /**
     * 隐藏软键盘
     */
    public void hintKeyBoard() {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Subscribe
    public void onEvent(Object o) {
        if (o instanceof String) {
            Msg msg = new Msg("你好呀", Msg.TYPE_RECEIVED);
            msgList.add(msg);
            adapter.notifyItemInserted(msgList.size() - 1);//调用适配器的notifyItemInserted()方法,用于通知适配器有新的数据插入
            msgRecyclerView.scrollToPosition(msgList.size() - 1);//将显示的数据定位到最后一行
        }
    }

    private void initAdapter() {
        Msg msg1 = new Msg("很高兴认识你！！！", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("你好呀！！！", Msg.TYPE_SENT);
        msgList.add(msg2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //msgRecyclerView.addItemDecoration(new SpaceItemDecoration(4));//这里设置了RecyclerView子项的间隔
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
    }

    private void initView() {
        msgRecyclerView = findViewById(R.id.chat_recyclerView);
        btnSend = findViewById(R.id.btn_chat_message_send);
        etMessage = findViewById(R.id.et_chat_message);
        llBack = findViewById(R.id.ll_back);
        tvTitle = findViewById(R.id.tv_title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        instance = null;
    }
}
