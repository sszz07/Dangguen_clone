package org.techtown.dang_guen.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.techtown.dang_guen.chatting.chatting_ApiClient;
import org.techtown.dang_guen.chatting.chatting_Interface;
import org.techtown.dang_guen.chatting.chatting_main_data;
import org.techtown.dang_guen.chatting.chatting_room;
import org.techtown.dang_guen.chatting.chatting_room_data;
import org.techtown.dang_guen.chatting.chatting_service;
import org.techtown.dang_guen.my_dang_guen.my_dang_guen_like;
import org.techtown.dong_nae_life.R;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class product_other_select extends AppCompatActivity {
    LinearLayout product_other_select;
    TextView subject_textview, content_textview, price_textview, seller_nick_textview, product_other_select_category, product_other_select_time, product_other_select_like, product_other_select_like_count, product_other_select_point1, product_other_select_chatting, product_other_select_chatting_count, product_other_select_point2, product_other_select_hits_textview, product_other_select_hits_count_textview;
    ImageView seller_image_imageview;
    ImageButton like_하기, like_취소;
    Button chatting_certification_button, product_other_select_chatting_button;
    int idx, like_image_idx, like_count, hits_count, room_nubmer;
    String seller_image, seller_nick, subject, content, category, price, image, 현재유저_phone, seller_phone;
    RecyclerView recyclerView;
    List<product_data> list = new ArrayList<>();
    product_my_other_select_adapter adapter;
    PrintWriter sendWriter;
    Button chatting_button;

    //게시판 번호저장하기
    ArrayList<String> 게시판번호_arraylist = new ArrayList<>();

    long present_time;
    long chatting_main_out_my_check_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_other_select);
        /*-------------------------------------------------------------------------------------------------------------*/
        SharedPreferences preferences = getSharedPreferences("현재유저", 0);
        String 현재유저_phone_number = preferences.getString("user_number", "");
        /*-------------------------------------------------------------------------------------------------------------*/


        seller_image_imageview = (ImageView) findViewById(R.id.product_other_select_seller_image);
        subject_textview = (TextView) findViewById(R.id.product_other_select_subject);
        content_textview = (TextView) findViewById(R.id.product_other_select_content);
        price_textview = (TextView) findViewById(R.id.product_other_select_price);
        seller_nick_textview = (TextView) findViewById(R.id.product_other_select_seller_nick);
        product_other_select_category = (TextView) findViewById(R.id.product_other_select_category);
        product_other_select_time = (TextView) findViewById(R.id.product_other_select_time);
        chatting_certification_button = (Button) findViewById(R.id.product_other_select_chatting_button);
        chatting_button = (Button) findViewById(R.id.chatting_button);

        chatting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(product_other_select.this, chatting_room.class);
                startActivity(intent);
            }
        });


        recyclerView = findViewById(R.id.product_other_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        Intent intent = getIntent();
        idx = intent.getIntExtra("idx", 0);
        hits_count = intent.getIntExtra("hits_count", 0);
        like_image_idx = intent.getIntExtra("image_number", 0);
        seller_image = intent.getStringExtra("seller_image");
        seller_nick = intent.getStringExtra("seller_nick");
        subject = intent.getStringExtra("subject");
        content = intent.getStringExtra("content");
        category = intent.getStringExtra("category");
        price = intent.getStringExtra("price");
        seller_phone = intent.getStringExtra("phone");
        present_time = intent.getLongExtra("present_time", 0);
        image = intent.getStringExtra("image");


       chatting_main_room_number_select(현재유저_phone_number, seller_phone);
        product_other_select_image(idx);

        /*게시물 조회수 코드----------------------------------------------------------------------------------------------------------------*/
        product_other_select_hits_count_textview = (TextView) findViewById(R.id.product_other_select_hits_count_textview);
        product_other_select_hits_count_textview.setText(String.valueOf(hits_count));

        //쉐어드에서 가져온 값을 어레이 넣어준다
        String 게시판번호int값_String변환_key값 = Integer.toString(idx);
        System.out.println(게시판번호int값_String변환_key값+"게시판번호int값_String변환_key값게시판번호int값_String변환_key값");
        게시판번호_arraylist = getStringArrayPref("게시판번호");
        //나의 아이디가 존재하면 카운트 안올라감

        //쉐어드에 값이 아예 없으면 만들기
        if (게시판번호_arraylist.size() == 0) {
            int hits_count2 = hits_count + 1;
            게시판번호_arraylist.add(게시판번호int값_String변환_key값 + "");
            product_info_hits_update(idx, hits_count2);
        }
        //쉐어드에 값이 있지만 현재 게시판 번호가 없을때
        else {
            //쉐어드값과 다르면 추가하기
            //contain 리스트에 포함이 되어있는지
            if (!게시판번호_arraylist.contains(게시판번호int값_String변환_key값)) {
                int hits_count2 = hits_count + 1;
                게시판번호_arraylist.add(게시판번호int값_String변환_key값 + "");
                product_other_select_hits_count_textview.setText(String.valueOf(hits_count2));
                product_info_hits_update(idx, hits_count2);
            }

        }

        //쉐어드에 값이 있을때

        /*게시물 채팅 코드----------------------------------------------------------------------------------------------------------------*/
        product_other_select_chatting = (TextView) findViewById(R.id.product_other_select_chatting);
        product_other_select_chatting_count = (TextView) findViewById(R.id.product_other_select_chatting_count);
        product_other_select_point1 = (TextView) findViewById(R.id.product_other_select_point1);
        product_other_select_chatting.setVisibility(View.GONE);
        product_other_select_chatting_count.setVisibility(View.GONE);
        product_other_select_point1.setVisibility(View.GONE);
        /*------------------------------------------------------------------------------------------------------------------------------*/



        /*채팅하기----------------------------------------------------------------------------------------------------------------*/
        product_other_select_chatting_button = (Button) findViewById(R.id.product_other_select_chatting_button);
        product_other_select_chatting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Log.e("chatting_main", "b가 들어왔음을 알림");
                            Socket socket = chatting_service.getSocket();
                            sendWriter = new PrintWriter(socket.getOutputStream());
                            sendWriter.print(null + ",");
                            sendWriter.print(null + ",");
                            sendWriter.print(현재유저_phone_number + ",");
                            sendWriter.print("my_phone_number" + ",");
                            sendWriter.print("-1" + ",");
                            sendWriter.print("chatting_room" + ",");
                            sendWriter.print(null + ",");
                            sendWriter.print(null + ",");
                            sendWriter.print("-1" + ",");
                            sendWriter.print(null + ",");
                            sendWriter.print("입장" + ",");
                            sendWriter.print(null + ",");
                            sendWriter.print(null + ",");
                            sendWriter.println("사진없음");
                            sendWriter.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();




                chatting_main_delete_date_update(room_nubmer, 현재유저_phone_number);
                Intent intent = new Intent(product_other_select.this, chatting_room.class);
                intent.putExtra("product_number", idx);
                intent.putExtra("product_image", image);
                intent.putExtra("product_price", price);
                intent.putExtra("product_subject", subject);
                intent.putExtra("your_nick", seller_nick);
                intent.putExtra("my_phone_number", 현재유저_phone_number);
                intent.putExtra("your_phone_number", seller_phone);
                intent.putExtra("other_user_image", seller_image);
                intent.putExtra("room_number", room_nubmer);
                intent.putExtra("chatting_main_out_my_check_time", chatting_main_out_my_check_time);
                startActivity(intent);


            }
        });
        /*------------------------------------------------------------------------------------------------------------------------------*/


        /*게시물 like 코드----------------------------------------------------------------------------------------------------------------*/
        //좋아요 select하기
        product_info_like_select(idx);

        //like_nick값
        현재유저_phone = preferences.getString("user_number", null);

        //이미지값 insert
        image = intent.getStringExtra("image");


        product_other_select = (LinearLayout) findViewById(R.id.product_other_select);
        like_하기 = (ImageButton) findViewById(R.id.product_other_select_like_button_age);
        like_취소 = (ImageButton) findViewById(R.id.product_other_select_like_button_after);
        product_other_select_like = (TextView) findViewById(R.id.product_other_select_like);
        product_other_select_like_count = (TextView) findViewById(R.id.product_other_select_like_count);
        product_other_select_point2 = (TextView) findViewById(R.id.product_other_select_point2);


        //https://stackoverflow.com/questions/31746300/how-to-show-snackbar-at-top-of-the-screen
        like_하기.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int like_갯수증가 = like_count + 1;
                product_other_select_like.setVisibility(View.VISIBLE);
                like_취소.setVisibility(View.VISIBLE);
                like_하기.setVisibility(View.GONE);
                product_other_select_like_count.setText(String.valueOf(like_갯수증가));
                //좋아요 버튼을 눌렀을때 스낵바가 나오면서 관심목록보기 페이지로 이동을 할수가 있다
                Snackbar snackbar = Snackbar.make(product_other_select, "관심목록에 추가되었습니다.", Snackbar.LENGTH_SHORT);
                snackbar.setAction("관심목록보기", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /**
                         * this는 안되고 getApplicationContext는 되는 이유*/
                        Intent intent = new Intent(getApplicationContext(), my_dang_guen_like.class);
                        startActivity(intent);
                    }
                });

                //스낵바 뷰객체 가져오기
//                View snackbarView = snackbar.getView();
//
//                //스낵바 텍스트 가져오기
//                TextView snackbarText = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
//
//                //스낵바 글씨 블랙
//                snackbarText.setTextColor(Color.BLACK);
//
//                //액션 글씨 색변경
//                snackbar.setActionTextColor(Color.parseColor("#DC9146"));
//
//                //스낵바 배경색 변경
//                snackbarView.setBackgroundColor(Color.WHITE);
//
//                snackbar.show();

                //좋아요 정보 insert
                product_info_like_insert(like_갯수증가);
            }
        });


        //좋아요 취소버튼+++++++++++++++++
        like_취소.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(like_count + "like_count_like_취소");
                int 좋아요_갯수하락 = like_count - 1;
                System.out.println(좋아요_갯수하락 + "좋아요_갯수하락");
                //만약에 내가 취소했을때 0개라면
                if (like_count == 0) {
                    product_other_select_like.setVisibility(View.GONE);

                    product_other_select_point2.setVisibility(View.GONE);

                    product_other_select_like_count.setVisibility(View.GONE);

                }

                like_취소.setVisibility(View.GONE);
                like_하기.setVisibility(View.VISIBLE);
                product_other_select_like_count.setText(String.valueOf(좋아요_갯수하락));

                product_info_like_delete(좋아요_갯수하락);
            }
        });
        /*------------------------------------------------------------------------------------------------------------------------------*/


        Glide.with(this)
                .load("http://52.79.180.89/dang_guen" + seller_image)
                .apply(new RequestOptions().circleCrop())
                .into(seller_image_imageview);

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


        //레이아웃에 보이게 하기
        product_other_select_time.setText(시간값);
        subject_textview.setText(subject);
        content_textview.setText(content);
        price_textview.setText(price+"원");
        seller_nick_textview.setText(seller_nick);
        product_other_select_category.setText(category);

        product_my_other_select_adapter adapter = new product_my_other_select_adapter(product_other_select.this, list);
        recyclerView.setAdapter(adapter);

    }


    //이미지 값 가져오기
    private void product_other_select_image(int idx) {
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
        setRecyclerViewEvent();
        recyclerView.setAdapter(adapter);
        list = lists;
    }


    //좋아요 정보 insert
    private void product_info_like_insert(int product_info_like_count) {
        product_ApiInterface service = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<product_data> call = service.product_like_insert(subject, content, seller_image, seller_nick, category, price, image, idx, 현재유저_phone, present_time, product_info_like_count, "중고거래");
        call.enqueue(new Callback<product_data>() {
            @Override
            public void onResponse(@NonNull Call<product_data> call, @NonNull Response<product_data> response) {
                if (response.isSuccessful() && response.body() != null) {
                    product_info_like_select(idx);

                }
            }

            @Override
            public void onFailure(@NonNull Call<product_data> call, @NonNull Throwable t) {
                Log.e("insert", "에러 : " + t.getMessage());

            }

            private void onError(String message) {
                Log.e("insertPerson()", "onResponse() 에러 : " + message);
            }

            private void onSuccess(String message) {
                Log.e("insertPerson()", "onResponse() 성공 : " + message);

            }
        });
    }


    //좋아요 취소
    private void product_info_like_delete(int product_info_like_count) {
        product_ApiInterface ApiInterface = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<product_data> call = ApiInterface.product_like_delete(현재유저_phone, idx, product_info_like_count);
        call.enqueue(new Callback<product_data>() {
            @Override
            public void onResponse(@NonNull Call<product_data> call, @NonNull Response<product_data> response) {
                product_info_like_select(idx);

            }

            @Override
            public void onFailure(@NonNull Call<product_data> call, @NonNull Throwable t) {
                Log.e("insert", "에러 : " + t.getMessage());

            }
        });
    }


    //좋아요 갯수 가져오기
    private void product_info_like_select(int idx) {
        product_ApiInterface ApiInterface = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<List<product_data>> call = ApiInterface.product_like_select(idx);
        call.enqueue(new Callback<List<product_data>>() {
            @Override
            public void onResponse(Call<List<product_data>> call, Response<List<product_data>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    onGetResult2(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<product_data>> call, Throwable t) {

            }
        });

    }

    private void onGetResult2(List<product_data> lists) {
        this.list = lists;
        like_count = list.size();
        //like의 갯수 0++++++++++++
        if (list.size() == 0) {
            product_other_select_like.setVisibility(View.GONE);

            product_other_select_point2.setVisibility(View.GONE);

            product_other_select_like_count.setVisibility(View.GONE);

            like_취소.setVisibility(View.GONE);
        }
        //like의 갯수가 0개 이상++++++++++++
        else {
            product_other_select_point2.setVisibility(View.VISIBLE);

            product_other_select_like_count.setVisibility(View.VISIBLE);

            //like카운트 settext++++++++++++
            product_other_select_like_count.setText(String.valueOf(list.size()));
        }


        //현재 게시판의 좋아요를 눌렀는지 안눌렀는지 확인하는 코드++++++++++++
        for (int i = 0; i < list.size(); i++) {
            //만약에 내가 좋아요를 안눌렀다면 like_하는중 버튼 없애기++++++++++++
            if (현재유저_phone.equals(list.get(i).getLike_phone())) {
                like_하기.setVisibility(View.GONE);
                like_취소.setVisibility(View.VISIBLE);
            } else {
                like_취소.setVisibility(View.GONE);
                like_하기.setVisibility(View.VISIBLE);
            }
        }
    }


    /*게시물 조회수 코드----------------------------------------------------------------------------------------------------------------*/
    private void setStringArrayPref(String key, ArrayList<String> values) {
        System.out.println(key + "key");
        System.out.println(values + "values");

        SharedPreferences preferences = getSharedPreferences("현재유저", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        JSONArray a = new JSONArray();

        for (int i = 0; i < values.size(); i++) {

            a.put(values.get(i));
        }

        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }

        editor.apply();
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


    private void product_info_hits_update(int idx, int hits) {
        product_ApiInterface product_ApiInterface = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<product_data> call = product_ApiInterface.product_info_hits_update(idx, hits);
        call.enqueue(new Callback<product_data>() {
            @Override
            public void onResponse(@NonNull Call<product_data> call, @NonNull Response<product_data> response) {

            }

            @Override
            public void onFailure(@NonNull Call<product_data> call, @NonNull Throwable t) {
                Log.e("updatePerson()", "에러 : " + t.getMessage());
            }
        });
    }
    /*------------------------------------------------------------------------------------------------------------------------------*/


    @Override
    public void onResume() {
        super.onResume();
        setStringArrayPref("게시판번호", 게시판번호_arraylist);
        product_info_like_select(idx);
    }


    //your 노티알림 업데이트
    /*-------------------------------------------------------------------------------------------------------------*/
    private void chatting_main_delete_date_update(int chatting_int_room_number, String chatting_main_room_check) {
        chatting_Interface chatting_Interface = chatting_ApiClient.getApiClient().create(chatting_Interface.class);
        Call<chatting_room_data> call = chatting_Interface.chatting_main_delete_date_update(chatting_int_room_number, chatting_main_room_check);
        call.enqueue(new Callback<chatting_room_data>() {
            @Override
            public void onResponse(@NonNull Call<chatting_room_data> call, @NonNull Response<chatting_room_data> response) {
                if (response.isSuccessful() && response.body() != null) {

                }
            }

            @Override
            public void onFailure(@NonNull Call<chatting_room_data> call, @NonNull Throwable t) {
                Log.e("insertPerson()_your", "에러 : " + t.getMessage());
            }
        });
    }
    /*-------------------------------------------------------------------------------------------------------------*/

    //채팅방 데이터값 가져오기
    private void chatting_main_room_number_select(String 현재유저, String seller_phone) {
        chatting_Interface chatting_Interface = chatting_ApiClient.getApiClient().create(chatting_Interface.class);
        Call<chatting_main_data> call = chatting_Interface.chatting_main_room_number_select(현재유저, seller_phone);
        call.enqueue(new Callback<chatting_main_data>() {
            @Override
            public void onResponse(@NonNull Call<chatting_main_data> call, @NonNull Response<chatting_main_data> response) {
                if (response.isSuccessful() && response.body() != null) {
                    room_nubmer = response.body().getChatting_main_idx();
                    chatting_main_out_my_check_time = response.body().getChatting_main_out_my_check_time();

                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<chatting_main_data> call, @NonNull Throwable t) {
                Log.e("chatting_room_select_my_message", "에러 : " + t.getMessage());
            }
        });
    }


    //리사이클러뷰 이벤트 설정
    private void setRecyclerViewEvent() {
        /**
         * Implementation of the SnapHelper supporting pager style snapping in either vertical or horizontal orientation.
         * PagerSnapHelper can help achieve a similar behavior to androidx.viewpager.widget.ViewPager. Set both RecyclerView and the items of the RecyclerView.Adapter to have android.view.ViewGroup.LayoutParams.MATCH_PARENT height and width and then attach PagerSnapHelper to the RecyclerView using attachToRecyclerView(RecyclerView).*/
        /*
         snapHelper = new PagerSnapHelper();
        * 역할?뷰페이저와 유사하게 동작을 할수있는 클래스
        * 책임?snapHelper를 사용하기 위해서
        * 협력? snapHelper.attachToRecyclerView(recyclerView);
        1.PagerSnapHelper를 사용하기 위해 attachToRecyclerView가 연결을 해주는 역할이다
        * */
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        /**
         * Add an RecyclerView.ItemDecoration to this RecyclerView. Item decorations can affect both measurement and drawing of individual item views.
         * Item decorations are ordered. Decorations placed earlier in the list will be run/queried/drawn first for their effects on item views. Padding added to views will be nested; a padding added by an earlier decoration will mean further item decorations in the list will be asked to draw/pad within the previous decoration's given area.
         * Params:
         * decor – Decoration to add
         * */

              /*
        recyclerView.addItemDecoration(new LinePagerIndicatorDecoration());
        * 역할?리사이클러뷰의 아이템을 꾸며주는 메소드 이다
        * 책임?리사이클러뷰의 아이템을 사이즈등 꾸며줌을 해준다
        * 사용하는 이유-
          협력?
          1.
        * */
        recyclerView.addItemDecoration(new LinePagerIndicatorDecoration());
        /**
         * Attaches the SnapHelper to the provided RecyclerView, by calling RecyclerView.setOnFlingListener(RecyclerView.OnFlingListener). You can call this method with null to detach it from the current RecyclerView.
         * Params:
         * recyclerView – The RecyclerView instance to which you want to add this helper or null if you want to remove SnapHelper from the current RecyclerView.
         * Throws:
         * IllegalArgumentException – if there is already a RecyclerView.OnFlingListener attached to the provided RecyclerView.*/

        /*
        recyclerView.setOnFlingListener(null);
        * 역할?Fling 동작을 사용할수가 있다
        * 책임?Fling 동작을 눌값은 fling
        * 협력?
        *
        * */
        recyclerView.setOnFlingListener(null);

              /*
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        * 역할?PagerSnapHelper과 연결을 할수있게 하느 메소드
        * 책임?PagerSnapHelper를 연결을 해줄수 있게 한다
        * 협력?PagerSnapHelper snapHelper = new PagerSnapHelper();
        1.PagerSnapHelper에서 클래스 객체를 만들어서 매개변수에 리사이클러뷰 레이아웃 객체를 넣어준다
        * */
        snapHelper.attachToRecyclerView(recyclerView);
    }
}