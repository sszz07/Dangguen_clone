package org.techtown.dang_guen.dong_nae_life;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.techtown.dong_nae_life.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dong_nae_life_my_select_together extends AppCompatActivity {
    TextView subject_textview, content_textview, dong_nae_life_my_select_together_person_textview, dong_nae_life_my_select_together_age_textview, dong_nae_life_my_select_together_date_textview, dong_nae_life_my_select_together_time_textview, dong_nae_life_my_select_together_location_textview,dong_nae_life_my_select_together_category_textview;
    int idx;
    String subject, content, age, dong_nae_life_main_together_date,dong_nae_life_main_together_person,dong_nae_life_main_together_nick,dong_nae_life_main_together_location,dong_nae_life_main_together_clock,categpry;
    RecyclerView recyclerView;
    dong_nae_life_my_other_select_adapter adapter;
    List<dong_nae_life_data> list = new ArrayList<>();
    ImageView dong_nae_life_other_select_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dong_nae_life_my_select_together);


//        recyclerView = findViewById(R.id.dong_nae_life_select_other_recyclerview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        subject_textview = (TextView) findViewById(R.id.dong_nae_life_my_select_together_subject_textview);
        content_textview = (TextView) findViewById(R.id.dong_nae_life_my_select_together_content_textview);
        dong_nae_life_my_select_together_person_textview = (TextView) findViewById(R.id.dong_nae_life_my_select_together_person_textview);
        dong_nae_life_my_select_together_age_textview = (TextView) findViewById(R.id.dong_nae_life_my_select_together_age_textview);
        dong_nae_life_my_select_together_date_textview = (TextView) findViewById(R.id.dong_nae_life_my_select_together_date_textview);
        dong_nae_life_my_select_together_time_textview = (TextView) findViewById(R.id.dong_nae_life_my_select_together_time_textview);
        dong_nae_life_my_select_together_location_textview = (TextView) findViewById(R.id.dong_nae_life_my_select_together_location_textview);
        dong_nae_life_my_select_together_category_textview = (TextView) findViewById(R.id.dong_nae_life_my_select_together_category_textview);
         Intent intent = getIntent();
        idx = intent.getIntExtra("idx", 0);
        subject = intent.getStringExtra("dong_nae_life_main_together_subject");
        content = intent.getStringExtra("dong_nae_life_main_together_content");
        age = intent.getStringExtra("dong_nae_life_main_together_age");
        dong_nae_life_main_together_date = intent.getStringExtra("dong_nae_life_main_together_date");
        dong_nae_life_main_together_person = intent.getStringExtra("dong_nae_life_main_together_person");
        dong_nae_life_main_together_location = intent.getStringExtra("dong_nae_life_main_together_location");
        dong_nae_life_main_together_nick = intent.getStringExtra("dong_nae_life_main_together_nick");
        dong_nae_life_main_together_clock = intent.getStringExtra("dong_nae_life_main_together_clock");
        categpry = intent.getStringExtra("dong_nae_life_main_together_clock");



        //레이아웃 데이터값 subject
        subject_textview.setText(subject);
        content_textview.setText(content);
        dong_nae_life_my_select_together_person_textview.setText(dong_nae_life_main_together_person);
        dong_nae_life_my_select_together_age_textview.setText(age);
        dong_nae_life_my_select_together_date_textview.setText(dong_nae_life_main_together_date);
        dong_nae_life_my_select_together_time_textview.setText(dong_nae_life_main_together_clock);
        dong_nae_life_my_select_together_location_textview.setText(dong_nae_life_main_together_location);


//        Glide.with(this)
//                .asBitmap()
//                .load("http://52.79.180.89/dang_guen" + board_user_image)
//                .into(dong_nae_life_other_select_image);

//        class TIME_MAXIMUM {
//            public static final int SEC = 60;
//            public static final int MIN = 60;
//            public static final int HOUR = 24;
//            public static final int DAY = 30;
//            public static final int MONTH = 12;
//        }

//        long 현재시간 = System.currentTimeMillis();
//        long 시간계산 = (현재시간 - present_time) / 1000;
//
//        String 시간값 = null;
//        if (시간계산 < TIME_MAXIMUM.SEC) {
//            시간값 = "방금";
//        } else if ((시간계산 /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
//            시간값 = 시간계산 + "분전";
//        } else if ((시간계산 /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
//            시간값 = (시간계산) + "시간전";
//        } else if ((시간계산 /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
//            시간값 = (시간계산) + "일전";
//        } else if ((시간계산 /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
//            시간값 = (시간계산) + "달전";
//        } else {
//            시간값 = (시간계산) + "년전";
//        }



    }




}
