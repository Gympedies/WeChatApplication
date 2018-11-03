package com.example.lyy.wechatapplication;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class ChatClientActivity extends AppCompatActivity {
    private ListView chatContentList = findViewById(R.id.chatContentList);
    private EditText messageEditText = findViewById(R.id.messageEditText);
    private List<ChatContent> contents = new ArrayList<>();
    private ClientThread clientThread;
    private ChatContentAdapter adapter;
    private String userName;//从登陆获取
    private String targetName;//从好友列表获取
    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String chatContent = msg.getData().getString("read");
            ChatContent content = new ChatContent();
            content.setContent(chatContent);
            content.setMe(false);
            contents.add(content);
            adapter.notifyDataSetChanged();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_client);
        adapter = new ChatContentAdapter(this,R.layout.chat_layout,contents);
        chatContentList.setAdapter(adapter);
        clientThread = new ClientThread(handler);
        new Thread(clientThread).start();
    }

    public void send(View view) {
        Message msg = Message.obtain();
        String inputMessage = messageEditText.getText().toString().trim();
        msg.getData().putString("write",userName+":"+targetName+":"+inputMessage);
        clientThread.revHandler.sendMessage(msg);
        ChatContent content = new ChatContent();
        content.setMe(true);
        content.setContent(inputMessage);
        contents.add(content);
        messageEditText.setText("");
    }
}
