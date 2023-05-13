package org.techtown.dang_guen.my_dang_guen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import org.techtown.dang_guen.chatting.chatting_ApiClient;
import org.techtown.dang_guen.chatting.chatting_Interface;
import org.techtown.dang_guen.chatting.chatting_main_adapter;
import org.techtown.dang_guen.chatting.chatting_main_data;
import org.techtown.dang_guen.dong_nae_life.dong_nae_life_reply_update;
import org.techtown.dang_guen.home.home_adapter;
import org.techtown.dang_guen.home.product_ApiClient;
import org.techtown.dang_guen.home.product_ApiInterface;
import org.techtown.dang_guen.home.product_data;
import org.techtown.dang_guen.home.product_my_select;
import org.techtown.dang_guen.home.product_other_select;
import org.techtown.dong_nae_life.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class my_dang_guen_complete_select_user extends AppCompatActivity {

    RecyclerView recyclerView;
    my_dang_guen_complete_select_user_adapter adapter;
    my_dang_guen_complete_select_user_adapter.ItemClickListener itemClickListener;
    List<chatting_main_data> list = new ArrayList<>();
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dang_guen_complete_select_user);
        SharedPreferences preferences = getSharedPreferences("현재유저", Context.MODE_PRIVATE);
        String SharedPreferences_phone = preferences.getString("user_number", "");

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.my_dang_guen_complete_select_user_pop_up);

        recyclerView = findViewById(R.id.activity_my_dang_guen_complete_select_user_recyclerview);
        my_dang_guen_complete_select_user_select(SharedPreferences_phone);

        Intent intent = getIntent();
        int idx = intent.getIntExtra("idx", 0);


        itemClickListener = new my_dang_guen_complete_select_user_adapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String my_phone_number = list.get(position).getChatting_main_my_number();

                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show(); // 다이얼로그 띄우기
                /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */

                // 위젯 연결 방식은 각자 취향대로~
                // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
                // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.


                // 아니오 버튼
                // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.
                Button cancle_button = dialog.findViewById(R.id.my_dang_guen_complete_select_user_cancel);
                cancle_button.findViewById(R.id.my_dang_guen_complete_select_user_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 원하는 기능 구현
                        dialog.dismiss(); // 다이얼로그 닫기
                    }
                });
                // 네 버튼
                dialog.findViewById(R.id.my_dang_guen_complete_select_user_yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        my_dang_guen_fragment_sell_complete_update(idx, "거래완료",my_phone_number);
                        dialog.dismiss();
                        onBackPressed();
                    }
                });
            }
        };
    }


    // INSERT 구문 은 WHERE 절을 지원하지 않으므로 쿼리가 실패합니다. id열이 고유하거나 기본 키 라고 가정합니다 .
    private void my_dang_guen_complete_select_user_select(String my_number) {
        System.out.println(my_number+"my_numbermy_numbermy_numbermy_number");
        chatting_Interface chatting_Interface = chatting_ApiClient.getApiClient().create(chatting_Interface.class);
        Call<List<chatting_main_data>> call = chatting_Interface.my_dang_guen_complete_select_user_select(my_number);
        call.enqueue(new Callback<List<chatting_main_data>>() {
            @Override
            public void onResponse(@NonNull Call<List<chatting_main_data>> call, @NonNull Response<List<chatting_main_data>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    onGetResult(response.body());

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<chatting_main_data>> call, @NonNull Throwable t) {
                Log.e("selectPerson()", "에러 : " + t.getMessage());
            }
        });
    }

    private void onGetResult(List<chatting_main_data> lists) {
        adapter = new my_dang_guen_complete_select_user_adapter(this, lists, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        recyclerView.setAdapter(adapter);
        list = lists;
    }

    //텍스트 삭제
    private void my_dang_guen_fragment_sell_complete_update(int idx, String stauts,String phone) {
        product_ApiInterface ApiInterface = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<product_data> call = ApiInterface.my_dang_guen_fragment_sell_complete_update(idx, stauts,phone);
        call.enqueue(new Callback<product_data>() {
            @Override
            public void onResponse(@NonNull Call<product_data> call, @NonNull Response<product_data> response) {
            }

            @Override
            public void onFailure(@NonNull Call<product_data> call, @NonNull Throwable t) {

            }
        });
    }
}