package org.techtown.dang_guen.start_atcivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.techtown.dong_nae_life.R;

public class start_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);



        @SuppressLint("HandlerLeak") Handler handler = new Handler() {

            public void handleMessage(Message msg) {

                super.handleMessage(msg);

                //startActivity(intent);

                startActivity(new Intent(start_activity.this, MainActivity.class));

                finish();

            }

        };
        handler.sendEmptyMessageDelayed(0, 500);
    }
}