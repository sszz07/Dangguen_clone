package org.techtown.dang_guen;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.techtown.dang_guen.chatting.chatting_main;
import org.techtown.dang_guen.chatting.chatting_service;
import org.techtown.dong_nae_life.R;

import org.techtown.dang_guen.dong_nae_life.dong_nae_life_main;
import org.techtown.dang_guen.home.home;
import org.techtown.dang_guen.my_dang_guen.my_dang_guen;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class fragment_start extends AppCompatActivity {

    dong_nae_life_main dong_nae_life_main;
    org.techtown.dang_guen.chatting.chatting_main chatting_main;
    my_dang_guen my_dang_guen;
    home home;
    private String ip = "192.168.56.1";
    private int port = 1234;
    // 바텀 네비게이션
    BottomNavigationView bottomNavigationView;
    PrintWriter sendWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_start);

        home = new home();
        dong_nae_life_main = new dong_nae_life_main();
        chatting_main = new chatting_main();
        my_dang_guen = new my_dang_guen();

        // 바텀 네비게이션
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // 초기 플래그먼트 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.start_activity, home).commitAllowingStateLoss();

        // 바텀 네비게이션
        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        //서비스 시작하기
        Intent intent = new Intent(getApplicationContext(), chatting_service.class);
        intent.putExtra("ip", ip);
        intent.putExtra("port", port);
        //startService-서비스를 시작하기 위해서 있는것
        startService(intent);


        // 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.start_activity, home).commitAllowingStateLoss();
                        return true;
                    case R.id.dong_nae_life_main:
                        getSupportFragmentManager().beginTransaction().replace(R.id.start_activity, dong_nae_life_main).commitAllowingStateLoss();
                        return true;
                    case R.id.chatting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.start_activity, chatting_main).commitAllowingStateLoss();
                      return true;
                    case R.id.my_dang_guen:
                        getSupportFragmentManager().beginTransaction().replace(R.id.start_activity, my_dang_guen).commitAllowingStateLoss();
                        return true;
                }
                return true;
            }
        });
    }
}
