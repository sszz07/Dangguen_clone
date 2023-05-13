package org.techtown.dang_guen.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.techtown.dong_nae_life.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class junggo_category_digital extends AppCompatActivity {

    RecyclerView recyclerView;
    home_adapter adapter;
    home_adapter.ItemClickListener itemClickListener;
    List<product_data> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_junggo_category_digital);


        /*
         *SharedPreferences
         * 사전적 의미-공유(두사람 이상이 공동으로 사용)하는것을 선호(여러개 중에 특별히 종아함)
         *
         * 무엇인가? 앱 내부 로컬에 저장 또는 데이터를 파일로 저장을 한다
         *
         * 왜 사용하는가? 간단한 데이터를 저장 하며 사용하기 위해서
         *
         * 프래그먼트에서 어떻게 사용하는가?
         * 프래그먼트는 액티비티가 아니기에 this.getActivity()을 붙어줘야 하고 Context.MODE_PRIVATE 도 넣는다 그 다음은 같음
         * */
        SharedPreferences preferences = getSharedPreferences("현재유저", Context.MODE_PRIVATE);
        String SharedPreferences_phone = preferences.getString("user_number", "");


        itemClickListener = new home_adapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int idx = list.get(position).getIdx();
                Log.e("idx 데이터 확인", String.valueOf(idx));

                String subject = list.get(position).getSubject();
                Log.e("subject 데이터 확인", subject);

                String content = list.get(position).getContent();
                Log.e("content 데이터 확인", content);

                String price = list.get(position).getPrice();
                System.out.println(price + "price 확인");

                String phone = list.get(position).getPhone();
                System.out.println(phone + "폰번호 확인");

                String seller_image = list.get(position).getUser_image();
                Log.e("user_image 데이터 확인", seller_image);

                String seller_nick = list.get(position).getNick();
                Log.e("user_nick 데이터 확인", seller_nick);

                String category = list.get(position).getCategory();
                Log.e("category 데이터 확인", category);

                String chatting = list.get(position).getChatting();
                Log.e("chatting 데이터 확인", chatting);


                String image = list.get(position).getImage();
                Log.e("image 데이터 확인", image);

                int hits_count = list.get(position).getHits();

                long present_time = list.get(position).getPresent_time();

                int like_count = list.get(position).getLike_count();


//                //int형일때는 == 이것을 사용하지만 String형태일때는 이퀄스를 사용한다
                if (SharedPreferences_phone.equals(phone)) {
                    /*
                     * Intent란?
                     * 사전적 의미-강한 관심(마음이 끌려감),몰두(일에 정신을 다함)
                     *
                     * 무엇인가? 4대 컴포넌트(여러가지 구성을 하나로 만드는것)를 통신(소식을 전하다,자료를 보내다)하는것
                     *
                     * 왜 사용하는가? 각 액티비티에 통신을 하기 위해서
                     *
                     * 어떻게 사용하는가?
                     * 인텐트로 데이터를 보내는법
                     * 1)Intent intent = new Intent(getContext(), product_my_select.class);
                     * -인텐트의 객체를 새롭게 만들고 매개변수 안에는(현재 클래스,데이터를 보낼 클래스)
                     * [현재 액티비티는 여기고 저 클래스로 데이터를 보낼거임]
                     *
                     * 2)intent.putExtra("idx", idx);
                     * -putExtra("키값",데이터값)
                     * [저 클래스로 보내고 키값은 idx이거고 이거 없으면 데이터 못얻음,데이터는 idx 이거임임
                     *
                     * 3)startActivity(intent);
                     * [이동하자!!]
                     * */

                    Intent intent = new Intent(junggo_category_digital.this, product_my_select.class);
                    intent.putExtra("idx", idx);
                    intent.putExtra("subject", subject);
                    intent.putExtra("content", content);
                    intent.putExtra("price", price);
                    intent.putExtra("seller_image", seller_image);
                    intent.putExtra("seller_nick", seller_nick);
                    intent.putExtra("category", category);
                    intent.putExtra("like_count", like_count);
                    intent.putExtra("present_time", present_time);
                    intent.putExtra("hits_count", hits_count);
                    startActivity(intent);
                } else {

                    Intent intent = new Intent(junggo_category_digital.this, product_other_select.class);
                    intent.putExtra("idx", idx);
                    intent.putExtra("subject", subject);
                    intent.putExtra("content", content);
                    intent.putExtra("price", price);
                    intent.putExtra("seller_image", seller_image);
                    intent.putExtra("seller_nick", seller_nick);
                    intent.putExtra("category", category);
                    intent.putExtra("chatting", chatting);
                    intent.putExtra("image", image);
                    intent.putExtra("phone", phone);
                    intent.putExtra("like_count", like_count);
                    intent.putExtra("present_time", present_time);
                    intent.putExtra("hits_count", hits_count);
                    startActivity(intent);
                }
            }
        };
    }

    // INSERT 구문 은 WHERE 절을 지원하지 않으므로 쿼리가 실패합니다. id열이 고유하거나 기본 키 라고 가정합니다 .
    private void junggo_category_gagu_recycerview_select(String 디지털) {
        product_ApiInterface ApiInterface = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<List<product_data>> call = ApiInterface.junggo_category_recycerview_select(디지털);
        call.enqueue(new Callback<List<product_data>>() {
            @Override
            public void onResponse(@NonNull Call<List<product_data>> call, @NonNull Response<List<product_data>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    onGetResult(response.body());
                    for (int i = 0; i < response.body().size(); i++) {
                        System.out.println(response.body().get(i).getIdx() + "idx번호 확인");

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<product_data>> call, @NonNull Throwable t) {
                Log.e("selectPerson()", "에러 : " + t.getMessage());
            }
        });
    }

    private void onGetResult(List<product_data> lists) {
        adapter = new home_adapter(junggo_category_digital.this, lists, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        list = lists;
    }


    @Override
    public void onResume() {
        super.onResume();
        String 디지털 = "디지털 기기";
        recyclerView = (RecyclerView) findViewById(R.id.junggo_category_digital_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

        junggo_category_gagu_recycerview_select(디지털);
    }
}