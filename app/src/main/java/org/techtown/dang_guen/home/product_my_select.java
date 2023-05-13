package org.techtown.dang_guen.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.techtown.dong_nae_life.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class product_my_select extends AppCompatActivity {

    TextView subject_textview, content_textview, price_textview, product_my_select_category, product_my_select_time, product_my_select_click_counter, product_my_select_like, product_my_select_like_count,product_my_select_like_count_slide;
    ImageButton product_my_update_delete_button;
    int idx, hits_count, like_count;
    String subject, content, category, price;
    RecyclerView recyclerView;
    List<product_data> list = new ArrayList<>();
    product_my_other_select_adapter adapter;
    Dialog dilaog, dialog_delete;
    long present_time;
    ArrayList<String> 게시판번호_arraylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_my_select);

        dilaog = new Dialog(product_my_select.this);       // Dialog 초기화
        dilaog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dilaog.setContentView(R.layout.product_my_select_update_delete_dialog);

        dialog_delete = new Dialog(product_my_select.this);       // Dialog 초기화
        dialog_delete.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog_delete.setContentView(R.layout.product_my_select_delete_check_dialog);

        product_my_update_delete_button = (ImageButton) findViewById(R.id.product_my_update_delete_button);
        product_my_select_like = (TextView) findViewById(R.id.product_my_select_like);
        product_my_select_like_count = (TextView) findViewById(R.id.product_my_select_like_count);
        product_my_select_like_count_slide = (TextView) findViewById(R.id.product_my_select_like_count_slide);
        product_my_select_category = (TextView) findViewById(R.id.product_my_select_category);
        subject_textview = (TextView) findViewById(R.id.product_my_select_subject);
        content_textview = (TextView) findViewById(R.id.product_my_select_content);
        price_textview = (TextView) findViewById(R.id.product_my_select_price);
        product_my_select_time = (TextView) findViewById(R.id.product_my_select_time);
        recyclerView = findViewById(R.id.product_my_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        product_my_select_click_counter = (TextView) findViewById(R.id.product_my_select_click_counter);

        Intent intent = getIntent();
        idx = intent.getIntExtra("idx", 0);
        subject = intent.getStringExtra("subject");
        content = intent.getStringExtra("content");
        category = intent.getStringExtra("category");
        price = intent.getStringExtra("price");
        present_time = intent.getLongExtra("present_time", 0);
        hits_count = intent.getIntExtra("hits_count", 0);
        like_count = intent.getIntExtra("like_count", 0);

        if (like_count == 0) {
            product_my_select_like.setVisibility(View.GONE);
            product_my_select_like_count.setVisibility(View.GONE);
            product_my_select_like_count_slide.setVisibility(View.GONE);
        }
        String hits_count_string = String.valueOf(hits_count);
        String like_count_string = String.valueOf(like_count);


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


        //이미지 가지고 오기
        product_my_other_select_image(idx);

        //레이아웃에 보이게 하기
        product_my_select_click_counter.setText(hits_count_string);
        subject_textview.setText(subject);
        content_textview.setText(content);
        price_textview.setText(price);
        product_my_select_category.setText(category);
        product_my_select_time.setText(시간값);
        product_my_select_like_count.setText(like_count_string);

        product_my_update_delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });


    }


    //이미지 값 가져오기
    private void product_my_other_select_image(int idx) {
        product_ApiInterface ApiInterface = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<List<product_data>> call = ApiInterface.image_select(idx);
        call.enqueue(new Callback<List<product_data>>() {
            @Override
            public void onResponse(Call<List<product_data>> call, Response<List<product_data>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    onGetResult(response.body());

                }
            }

            @Override
            public void onFailure(Call<List<product_data>> call, Throwable t) {

            }
        });

    }

    private void onGetResult(List<product_data> lists) {
        adapter = new product_my_other_select_adapter(this, lists);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        setRecyclerViewEvent();
        list = lists;
    }


    //게시판 번호를 가져옴->다중이미지를 셀렉트하기 위해서
    private void product_info_update_select(int idx) {
        product_ApiInterface service = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<product_data> call = service.product_info_update_select(idx);
        call.enqueue(new Callback<product_data>() {
            @Override
            public void onResponse(@NonNull Call<product_data> call, @NonNull Response<product_data> response) {
                if (response.isSuccessful() && response.body() != null) {
                    /**
                     * 어떤 에러인지 확인하기
                     * End of input at line 1 column 1 path $
                     *
                     * */
                    String subject = response.body().getSubject();
                    String content = response.body().getContent();
                    String price = response.body().getPrice();
                    String category = response.body().getCategory();
                    //레이아웃에 보이게 하기

                    subject_textview.setText(subject);
                    content_textview.setText(content);
                    price_textview.setText(price);
                    product_my_select_category.setText(category);


                    Intent intent = new Intent(product_my_select.this, product_update.class);
                    intent.putExtra("idx", idx);
                    intent.putExtra("subject", subject);
                    intent.putExtra("content", content);
                    intent.putExtra("category", category);
                    intent.putExtra("price", price);
                    startActivity(intent);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<product_data> call, @NonNull Throwable t) {
                Log.e("product_info_subject_select", "에러 : " + t.getMessage());
            }
        });
    }

    //게시판 번호를 가져옴->다중이미지를 셀렉트하기 위해서
    private void product_info_update_select2(int idx) {
        product_ApiInterface service = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<product_data> call = service.product_info_update_select(idx);
        call.enqueue(new Callback<product_data>() {
            @Override
            public void onResponse(@NonNull Call<product_data> call, @NonNull Response<product_data> response) {
                if (response.isSuccessful() && response.body() != null) {
                    /**
                     * 어떤 에러인지 확인하기
                     * End of input at line 1 column 1 path $
                     *
                     * */
                    String subject = response.body().getSubject();
                    String content = response.body().getContent();
                    String price = response.body().getPrice();
                    String category = response.body().getCategory();

                    //레이아웃에 보이게 하기
                    subject_textview.setText(subject);
                    content_textview.setText(content);
                    price_textview.setText(price);
                    product_my_select_category.setText(category);

                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<product_data> call, @NonNull Throwable t) {
                Log.e("product_info_subject_select", "에러 : " + t.getMessage());
            }
        });
    }


    //텍스트 삭제
    private void product_info_delete(int idx) {
        product_ApiInterface ApiInterface = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<product_data> call = ApiInterface.product_info_delete(idx);
        call.enqueue(new Callback<product_data>() {
            @Override
            public void onResponse(@NonNull Call<product_data> call, @NonNull Response<product_data> response) {

            }

            @Override
            public void onFailure(@NonNull Call<product_data> call, @NonNull Throwable t) {

            }
        });
    }


    //이미지 테이블 삭제
    private void product_multiimage_delete(int idx) {
        product_ApiInterface ApiInterface = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<product_data> call = ApiInterface.product_multiimage_delete(idx);
        call.enqueue(new Callback<product_data>() {
            @Override
            public void onResponse(@NonNull Call<product_data> call, @NonNull Response<product_data> response) {

            }

            @Override
            public void onFailure(@NonNull Call<product_data> call, @NonNull Throwable t) {

            }
        });
    }


    //다이얼로그 실행
    public void dialog() {
//        dilaog.getWindow().setGravity(Gravity.BOTTOM);
        dilaog.show(); // 다이얼로그 띄우기
        /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */

        // 위젯 연결 방식은 각자 취향대로~
        // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
        // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.


        // 아니오 버튼
        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.
        Button cancle_button = dilaog.findViewById(R.id.product_my_select_cancle_dialog_button);
        cancle_button.findViewById(R.id.product_my_select_cancle_dialog_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                dilaog.dismiss(); // 다이얼로그 닫기
            }
        });
        // 네 버튼
        dilaog.findViewById(R.id.product_my_select_delete_dialog_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_delete();
            }
        });
        dilaog.findViewById(R.id.product_my_select_update_dialog_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product_info_update_select(idx);
            }
        });
    }

    //다이얼로그 실행
    public void dialog_delete() {
//        dilaog.getWindow().setGravity(Gravity.BOTTOM);
        dialog_delete.show(); // 다이얼로그 띄우기
        /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */
        dilaog.dismiss();
        // 위젯 연결 방식은 각자 취향대로~
        // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
        // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.


        // 아니오 버튼
        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.
        Button cancle_button = dialog_delete.findViewById(R.id.product_my_select_cancle_check_button);
        cancle_button.findViewById(R.id.product_my_select_cancle_check_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                dialog_delete.dismiss(); // 다이얼로그 닫기
                product_info_update_select(idx);
            }
        });
        // 네 버튼
        dialog_delete.findViewById(R.id.product_my_select_delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product_info_delete(idx);
                product_multiimage_delete(idx);
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        dilaog.dismiss();


        product_info_update_select2(idx);
        //이미지 가지고 오기
        product_my_other_select_image(idx);

    }


    //    //리사이클러뷰 이벤트 설정
    private void setRecyclerViewEvent() {
        /**
         * Implementation of the SnapHelper supporting pager style snapping in either vertical or horizontal orientation.
         * PagerSnapHelper can help achieve a similar behavior to androidx.viewpager.widget.ViewPager. Set both RecyclerView and the items of the RecyclerView.Adapter to have android.view.ViewGroup.LayoutParams.MATCH_PARENT height and width and then attach PagerSnapHelper to the RecyclerView using attachToRecyclerView(RecyclerView).*/
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        /**
         * Add an RecyclerView.ItemDecoration to this RecyclerView. Item decorations can affect both measurement and drawing of individual item views.
         * Item decorations are ordered. Decorations placed earlier in the list will be run/queried/drawn first for their effects on item views. Padding added to views will be nested; a padding added by an earlier decoration will mean further item decorations in the list will be asked to draw/pad within the previous decoration's given area.
         * Params:
         * decor – Decoration to add
         * */
        recyclerView.addItemDecoration(new LinePagerIndicatorDecoration());
        /**
         * Attaches the SnapHelper to the provided RecyclerView, by calling RecyclerView.setOnFlingListener(RecyclerView.OnFlingListener). You can call this method with null to detach it from the current RecyclerView.
         * Params:
         * recyclerView – The RecyclerView instance to which you want to add this helper or null if you want to remove SnapHelper from the current RecyclerView.
         * Throws:
         * IllegalArgumentException – if there is already a RecyclerView.OnFlingListener attached to the provided RecyclerView.*/
        recyclerView.setOnFlingListener(null);
        snapHelper.attachToRecyclerView(recyclerView);
    }


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