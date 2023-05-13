package org.techtown.dang_guen.my_dang_guen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


import org.techtown.dang_guen.home.home_adapter;
import org.techtown.dang_guen.home.product_data;
import org.techtown.dang_guen.home.product_my_other_select_adapter;
import org.techtown.dang_guen.home.product_other_select;
import org.techtown.dong_nae_life.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class my_dang_guen_like extends AppCompatActivity {
    Button 전체_button_select, 중고거래_button_select, 동네생활_button_select,전체_button_white, 중고거래_button_white, 동네생활_button_white;
//    my_dang_guen_like_Fragment_adapter my_dang_guen_like_adapter;
//    ViewPager2 my_dang_guen_like_viewPager2;
    RecyclerView recyclerView;
//    ArrayList<Fragment> fragList = new ArrayList<Fragment>();
    List<my_dang_guen_data> list = new ArrayList<>();
    my_dang_guen_like_adapter adapter;
    String button_name,현재유저_phone,종고제품,동네생활,전체;
    int currentPage;


    my_dang_guen_like_adapter.ItemClickListener itemClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dang_guen_like);

        //폰번호 설정
        SharedPreferences preferences = getSharedPreferences("현재유저", Context.MODE_PRIVATE);
        현재유저_phone = preferences.getString("user_number", null);


        recyclerView = findViewById(R.id.my_dang_guen_like_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        /*버튼 선택 코드----------------------------------------------------------------------------------------------------------------*/
//        my_dang_guen_like_viewPager2 = findViewById(R.id.my_dang_guen_like_viewPager2);

        전체_button_select = (Button) findViewById(R.id.my_dang_guen_like_all_button_select);
        중고거래_button_select = (Button) findViewById(R.id.my_dang_guen_like_product_button_select);
        동네생활_button_select = (Button) findViewById(R.id.my_dang_guen_like_dong_nae_life_button_select);

        전체_button_white = (Button) findViewById(R.id.my_dang_guen_like_all_button_white);
        중고거래_button_white = (Button) findViewById(R.id.my_dang_guen_like_product_button_white);
        동네생활_button_white = (Button) findViewById(R.id.my_dang_guen_like_dong_nae_life_button_white);



        전체_button_select.setVisibility(View.INVISIBLE);
        전체_button_white.setVisibility(View.VISIBLE);

        //프래그먼트 선언
        //왜 객체로 생성을 해줘야 될까
        my_dang_guen_like_all my_dang_guen_like_all = new my_dang_guen_like_all();
        my_dang_guen_like_dong_nae_life my_dang_guen_like_dong_nae_life = new my_dang_guen_like_dong_nae_life();
        my_dang_guen_like_jung_go my_dang_guen_like_jung_go = new my_dang_guen_like_jung_go();

        //프래그먼트 리스트에 등록
//        fragList.add(my_dang_guen_like_all);
//        fragList.add(my_dang_guen_like_dong_nae_life);
//        fragList.add(my_dang_guen_like_jung_go);

        //어뎁터에 리스트 등록
//        my_dang_guen_like_adapter = new my_dang_guen_like_Fragment_adapter(this, fragList);

        //어뎁터 뷰페이져에 적용
//        my_dang_guen_like_viewPager2.setAdapter(my_dang_guen_like_adapter);

        //첫화면
        button_name ="전체";
        my_dang_guen_like_select(현재유저_phone,전체);

        //시작 버튼
        전체_button_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_name ="전체";
                my_dang_guen_like_select(현재유저_phone,button_name);
//                my_dang_guen_like_viewPager2.setCurrentItem(currentPage=0);
                전체_button_select.setVisibility(View.INVISIBLE);
                전체_button_white.setVisibility(View.VISIBLE);
                중고거래_button_select.setVisibility(View.VISIBLE);
                중고거래_button_white.setVisibility(View.INVISIBLE);
                동네생활_button_select.setVisibility(View.VISIBLE);
                동네생활_button_white.setVisibility(View.INVISIBLE);
            }
        });


        //중고거래 버튼을 눌렀을때
        //중고거래 선택버튼 없음
        //중고거래 흰색 버튼 있음
        //전체 선택버튼 있음
        //전체 흰색 버튼 없음
        //동네생활 선택버튼 있음
        //동네생활 흰색버튼 없음
        중고거래_button_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_name ="중고거래";
                System.out.println(현재유저_phone+button_name+"button_name");
                my_dang_guen_like_select(현재유저_phone,button_name);
//                my_dang_guen_like_viewPager2.setCurrentItem(currentPage = 1);
                중고거래_button_select.setVisibility(View.INVISIBLE);
                중고거래_button_white.setVisibility(View.VISIBLE);
                전체_button_select.setVisibility(View.VISIBLE);
                전체_button_white.setVisibility(View.INVISIBLE);
                동네생활_button_select.setVisibility(View.VISIBLE);
                동네생활_button_white.setVisibility(View.INVISIBLE);
            }
        });


        동네생활_button_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_name ="동네생활";
                my_dang_guen_like_select(현재유저_phone,button_name);
//                my_dang_guen_like_viewPager2.setCurrentItem(currentPage = 2);
                전체_button_select.setVisibility(View.VISIBLE);
                전체_button_white.setVisibility(View.INVISIBLE);
                중고거래_button_select.setVisibility(View.VISIBLE);
                중고거래_button_white.setVisibility(View.INVISIBLE);
                동네생활_button_select.setVisibility(View.INVISIBLE);
                동네생활_button_white.setVisibility(View.VISIBLE);
            }
        });


        //화면 변경 시 이벤트 설정
//        my_dang_guen_like_viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//
//                currentPage = position;
//System.out.println(currentPage+"currentPage");
//                if (position == 0 ) { //first Page
//                    button_name ="전체";
//                    my_dang_guen_like_select(현재유저_phone,button_name);
//                    전체_button_select.setVisibility(View.INVISIBLE);
//                    전체_button_white.setVisibility(View.VISIBLE);
//                    중고거래_button_select.setVisibility(View.VISIBLE);
//                    중고거래_button_white.setVisibility(View.INVISIBLE);
//                    동네생활_button_select.setVisibility(View.VISIBLE);
//                    동네생활_button_white.setVisibility(View.INVISIBLE);
//                } else if (position == 1 ) { //Last Page
//                    button_name ="중고거래";
//                    my_dang_guen_like_select(현재유저_phone,button_name);
//                    중고거래_button_select.setVisibility(View.INVISIBLE);
//                    중고거래_button_white.setVisibility(View.VISIBLE);
//                    전체_button_select.setVisibility(View.VISIBLE);
//                    전체_button_white.setVisibility(View.INVISIBLE);
//                    동네생활_button_select.setVisibility(View.VISIBLE);
//                    동네생활_button_white.setVisibility(View.INVISIBLE);
//                } else if (position == 2 ) {
//                    button_name ="동네생활";
//                    my_dang_guen_like_select(현재유저_phone,button_name);
//                    전체_button_select.setVisibility(View.VISIBLE);
//                    전체_button_white.setVisibility(View.INVISIBLE);
//                    중고거래_button_select.setVisibility(View.VISIBLE);
//                    중고거래_button_white.setVisibility(View.INVISIBLE);
//                    동네생활_button_select.setVisibility(View.INVISIBLE);
//                    동네생활_button_white.setVisibility(View.VISIBLE);
//                }
//            }
//        });
        /*------------------------------------------------------------------------------------------------------------------------------*/
    }



    //좋아요 선태하기
    private void my_dang_guen_like_select(String like_phone,String button_name) {
        my_dang_guen_info_ApiInterface ApiInterface = my_dang_guen_ApiClient.getApiClient().create(my_dang_guen_info_ApiInterface.class);
        Call<List<my_dang_guen_data>> call = ApiInterface.my_dang_guen_like_select(like_phone,button_name);
        call.enqueue(new Callback<List<my_dang_guen_data>>() {
            @Override
            public void onResponse(Call<List<my_dang_guen_data>> call, Response<List<my_dang_guen_data>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    onGetResult(response.body());

                }
            }

            @Override
            public void onFailure(Call<List<my_dang_guen_data>> call, Throwable t) {

            }
        });

    }

    private void onGetResult(List<my_dang_guen_data> lists) {
        adapter = new my_dang_guen_like_adapter(this, lists, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        list = lists;
    }


    @Override
    public void onResume() {
        super.onResume();

        my_dang_guen_like_select(현재유저_phone, "전체");
    }
}