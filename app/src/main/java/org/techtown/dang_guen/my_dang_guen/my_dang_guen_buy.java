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

public class my_dang_guen_buy extends AppCompatActivity {

    RecyclerView recyclerView;
    my_dang_guen_buy_adapter adapter;
    my_dang_guen_buy_adapter.ItemClickListener itemClickListener;
    List<product_data> list = new ArrayList<>();
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dang_guen_buy);
        SharedPreferences preferences = getSharedPreferences("현재유저", Context.MODE_PRIVATE);
        String SharedPreferences_phone = preferences.getString("user_number", "");

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.product_my_select_delete_check_dialog);

        recyclerView = findViewById(R.id.my_dang_guen_buy_recyclerview);
        my_dang_guen_buy_select(SharedPreferences_phone);


        itemClickListener = new my_dang_guen_buy_adapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show(); // 다이얼로그 띄우기

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
                        onBackPressed();
                    }
                });
            }
        };
    }



    // INSERT 구문 은 WHERE 절을 지원하지 않으므로 쿼리가 실패합니다. id열이 고유하거나 기본 키 라고 가정합니다 .
    private void my_dang_guen_buy_select(String my_number) {
        product_ApiInterface ApiInterface = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<List<product_data>> call = ApiInterface.my_dang_guen_buy_select(my_number);
        call.enqueue(new Callback<List<product_data>>() {
            @Override
            public void onResponse(@NonNull Call<List<product_data>> call, @NonNull Response<List<product_data>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    onGetResult(response.body());

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<product_data>> call, @NonNull Throwable t) {
                Log.e("selectPerson()", "에러 : " + t.getMessage());
            }
        });
    }

    private void onGetResult(List<product_data> lists) {
        adapter = new my_dang_guen_buy_adapter(this, lists, itemClickListener,dialog);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        recyclerView.setAdapter(adapter);
        list = lists;
    }



}