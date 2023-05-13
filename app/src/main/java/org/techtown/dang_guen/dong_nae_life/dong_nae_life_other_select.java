package org.techtown.dang_guen.dong_nae_life;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.techtown.dong_nae_life.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dong_nae_life_other_select extends AppCompatActivity {

    TextView subject_textview, content_textview, nick_textview, time_textview;
    int idx;
    String subject, content, board_user_image, board_user_nick;
    RecyclerView recyclerView;
    dong_nae_life_my_other_select_adapter adapter;
    List<dong_nae_life_data> list = new ArrayList<>();
    ImageView dong_nae_life_other_select_image;
    ArrayList<String> like_array = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dong_nae_life_other_select);

        //쉐어드 댓글 좋아요 가져오기
        like_array=getStringArrayPref("게시판번호");

        recyclerView = findViewById(R.id.dong_nae_life_other_image_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        subject_textview = (TextView) findViewById(R.id.dong_nae_life_select_other_subject);
        content_textview = (TextView) findViewById(R.id.dong_nae_life_other_select_content_textview);
        nick_textview = (TextView) findViewById(R.id.dong_nae_life_select_other_nick);
        time_textview = (TextView) findViewById(R.id.dong_nae_life_select_other_time);
        dong_nae_life_other_select_image = (ImageView) findViewById(R.id.dong_nae_life_select_other_image);

        Intent intent = getIntent();
        idx = intent.getIntExtra("idx", 0);
        subject = intent.getStringExtra("subject");
        content = intent.getStringExtra("content");
        board_user_image = intent.getStringExtra("board_user_image");
        board_user_nick = intent.getStringExtra("board_user_nick");
        long present_time = intent.getLongExtra("present_time", 0);
        System.out.println(board_user_image + "board_user_image");

        Glide.with(this)
                .load("http://52.79.180.89/dang_guen" + board_user_image)
                .apply(new RequestOptions().circleCrop())
                .into(dong_nae_life_other_select_image);

        class TIME_MAXIMUM {
            public static final int SEC = 60;
            public static final int MIN = 60;
            public static final int HOUR = 24;
            public static final int DAY = 30;
            public static final int MONTH = 12;
        }

        long 현재시간 = System.currentTimeMillis();
        long 시간계산 = (현재시간 - present_time) / 1000;

        String 시간값 = null;
        if (시간계산 < TIME_MAXIMUM.SEC) {
            시간값 = "방금";
        } else if ((시간계산 /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
            시간값 = 시간계산 + "분전";
        } else if ((시간계산 /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
            시간값 = (시간계산) + "시간전";
        } else if ((시간계산 /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
            시간값 = (시간계산) + "일전";
        } else if ((시간계산 /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
            시간값 = (시간계산) + "달전";
        } else {
            시간값 = (시간계산) + "년전";
        }


        //레이아웃 데이터값 설정
        nick_textview.setText(board_user_nick);
        time_textview.setText(시간값);
        subject_textview.setText(subject);
        content_textview.setText(content);
    }


    @Override
    protected void onResume() {
        super.onResume();
        dong_nae_life_my_select_image(idx);

    }


    //이미지 값 가져오기
    private void dong_nae_life_my_select_image(int idx) {
        dong_nae_life_ApiInterface ApiInterface = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
        Call<List<dong_nae_life_data>> call = ApiInterface.image_select(idx);
        call.enqueue(new Callback<List<dong_nae_life_data>>() {
            @Override
            public void onResponse(Call<List<dong_nae_life_data>> call, Response<List<dong_nae_life_data>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    onGetResult(response.body());

                }
            }

            @Override
            public void onFailure(Call<List<dong_nae_life_data>> call, Throwable t) {

            }
        });

    }

    private void onGetResult(List<dong_nae_life_data> lists) {
        adapter = new dong_nae_life_my_other_select_adapter(this, lists);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        list = lists;
    }


    /*댓글 좋아요 쉐어드 저장갑 불러오기*/
    private ArrayList getStringArrayPref(String key) {
        SharedPreferences preferences = getSharedPreferences("현재유저", Context.MODE_PRIVATE);
        String json = preferences.getString(key, null);
        ArrayList urls = new ArrayList();

        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }
}