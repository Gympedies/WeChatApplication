package com.example.lyy.wechatapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Hashtable;

public class ServerThread implements Runnable {
    private Socket socket;
    private Hashtable<String,ServerThread> chatRoom;
    private OutputStream out;
    private BufferedReader reader;

    public Socket getSocket() {
        return socket;
    }

    public ServerThread(Socket socket, Hashtable<String,ServerThread> chatRoom){
        this.socket = socket;
        this.chatRoom = chatRoom;
        try {
            out = socket.getOutputStream();
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void run() {
        try {
            String content = reader.readLine();
            while (content != null) {
                String[] wordParts = content.split(":");
                chatRoom.put(wordParts[0],this);
                ServerThread targetClient = chatRoom.get(wordParts[1]);
                targetClient.getSocket().getOutputStream().write(wordParts[2].getBytes("UTF-8"));
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                reader.close();
                out.close();
                socket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}
