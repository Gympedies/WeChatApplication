package com.example.lyy.wechatapplication;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
public class ChatServer {
    private static Hashtable<String,ServerThread> chatData = new Hashtable<>();
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(8888);
        while (true){
            final Socket socket = serverSocket.accept();
            new Thread(new ServerThread(socket,chatData)).start();
        }
    }
}
