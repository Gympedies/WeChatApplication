package com.example.lyy.wechatapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private HashMap<String,String> userData = new HashMap<>();
    public List<String> userList = new ArrayList<>();
    String address = "https://api.myjson.com/bins/14sr16";
    String info = new Fetcher(address).fetch();
    JSONArray jsa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            jsa = new JSONArray(info);
            for (int i=0; i<jsa.length();i++) {
                JSONObject jso = jsa.getJSONObject(i);
                userData.put(jso.getString("username"),jso.getString("password"));
                userList.add(jso.getString("username"));
            }
            }catch (JSONException e){
            e.printStackTrace();
        }
        for(String list: userList){
            Log.i("lyy",list.toString());
        }
    }

}
