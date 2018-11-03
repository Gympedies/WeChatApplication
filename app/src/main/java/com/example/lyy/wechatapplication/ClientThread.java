package com.example.lyy.wechatapplication;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
public class ClientThread implements Runnable {
    private OutputStream os;
    private BufferedReader br;
    private Socket socket;
    private String ipAddress = "192.168.1.103";
    private Handler handler;//更新UI
    public  Handler revHandler;//发送消息
    public ClientThread(Handler handler){
        this.handler = handler;
    }
    @Override
    public void run() {
        socket = new Socket();
        try{
            socket.connect(new InetSocketAddress(ipAddress,8888));
            br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
            os = socket.getOutputStream();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String content;
                    try{
                        while ((content = br.readLine())!= null){
                            Message msg = Message.obtain();
                            msg.getData().putString("read",content);
                            handler.sendMessage(msg);
                        }
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }).start();
            Looper.prepare();
            revHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    try {
                        os.write(msg.getData().getString("write").getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            Looper.loop();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
