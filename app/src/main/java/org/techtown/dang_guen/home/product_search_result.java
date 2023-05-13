package org.techtown.dang_guen.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;

import org.techtown.dang_guen.chatting.chatting_ApiClient;
import org.techtown.dang_guen.chatting.chatting_Interface;
import org.techtown.dang_guen.chatting.chatting_main_adapter;
import org.techtown.dang_guen.chatting.chatting_main_data;
import org.techtown.dang_guen.chatting.chatting_room;
import org.techtown.dang_guen.chatting.chatting_service;
import org.techtown.dang_guen.dong_nae_life.dong_nae_life_comment_adapter;
import org.techtown.dong_nae_life.R;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class product_search_result extends AppCompatActivity {
   /*
    ArrayList<FoodItem> foodItemArrayList, filteredList;
    역할?데이터의 목록의 값을 넣기 위해서
    책임?데이터의 목록의 값들을 넣어야만 한다
    협력?어레이리스트 객체를 만들기와 어댑터에 데이터값 메세지 보내기와 변수에 리스트 넣기와for문에 값 확인와 조건문으로 와~를 함께한다
    1.foodItemArrayList = new ArrayList<>(); 데이터 담을 객체 만들기
    2.foodAdapter = new FoodAdapter(foodItemArrayList, this); 어댑터에 데이터값 메세지 보내기
    3.foodItemArrayList.add(new FoodItem("asd")); 리스트에 데이터 추가하기
    4.for (int i = 0; i < foodItemArrayList.size(); i++) 으로 데이터 사이즈값 확인
    5.if (foodItemArrayList.get(i).getFoodName().toLowerCase().contains(searchText.toLowerCase()))????????
    6.filteredList.add(foodItemArrayList.get(i));????????/
    7.
    */

       /*
    ArrayList<FoodItem>  filteredList;
    역할?
    책임?
    협력?
    1.filteredList=new ArrayList<>();
    2.filteredList.clear();
    3.filteredList.add(foodItemArrayList.get(i));
    4.foodAdapter.filterList(filteredList);
    */

    ArrayList<product_search_data> foodItemArrayList, filteredList;

    /*
    FoodAdapter foodAdapter;
   역할?어레이리스트의 데이터값을 메인화면에 보일수있도록 연결해준다
   책임?메인화면에 데이터값을 보여주는 책임이 있다
   협력?
   1.foodAdapter = new FoodAdapter(foodItemArrayList, this); 연결해줄 어댑터객체를 생성
   2.recyclerView.setAdapter(foodAdapter); 리사이클러뷰와 어댑터를 연결해준다
   3.foodAdapter.notifyDataSetChanged(); 어댑터 새로고침해준다
   4.foodAdapter.filterList(filteredList);???????
   */
    product_search_adapter product_search_adapter;
    /*
    RecyclerView recyclerView;
   역할?메인화면에 리사이클러뷰로 보여주기 위해서
   책임?리사이클러뷰 id값 가져오기,레이아웃 매니저 설정,어댑터와 연결해줄 책임이 있다
   협력?
   1.recyclerView = findViewById(R.id.recyclerview); 레이아웃 리사이클러뷰 아이디값 가져오기
   2.recyclerView.setLayoutManager(linearLayoutManager); 리사이클러뷰에 어떤 레이아웃으로 들어가는지 확인
   3.recyclerView.setAdapter(foodAdapter); 어댑터와 연결하기 위해서
   */

    LinearLayoutManager linearLayoutManager;
    String result_data;
    RecyclerView recyclerView, product_search2;
    home_adapter adapter;
    home_adapter.ItemClickListener itemClickListener;
    List<product_data> list = new ArrayList<>();
    EditText product_search_result_edittext;
    Button product_search_result_가격, product_search_result_정확도순, product_search_result_최신순;
    Dialog product_search_result_가격_dialog, product_search_result_최신순_정확순_dialog;
    String style = null;
    ImageButton product_search_result_back_button;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search_result);


        Intent intent = getIntent();
        result_data = intent.getStringExtra("data");

        product_search_result_최신순_정확순_dialog = new Dialog(product_search_result.this);       // Dialog 초기화
        product_search_result_최신순_정확순_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        product_search_result_최신순_정확순_dialog.setContentView(R.layout.product_search_reulst_recent_popup);

        product_search_result_가격_dialog = new Dialog(product_search_result.this);       // Dialog 초기화
        product_search_result_가격_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        product_search_result_가격_dialog.setContentView(R.layout.product_search_reulst_price_popup);


        product_search_result_edittext = findViewById(R.id.product_search_result_edittext);
        product_search_result_edittext.setText(result_data);


        product_search_result_가격 = (Button) findViewById(R.id.product_search_result_가격);

        product_search_result_back_button = (ImageButton) findViewById(R.id.product_search_result_back_button);

        //백버튼
        product_search_result_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.product_search_result_recyclerview);
        product_search2 = findViewById(R.id.product_search2_recyclerview);
        product_search_result(result_data, style,0,0);

        SharedPreferences preferences = this.getSharedPreferences("현재유저", Context.MODE_PRIVATE);
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

                    Intent intent = new Intent(product_search_result.this, product_my_select.class);
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
                    Intent intent = new Intent(product_search_result.this, product_other_select.class);
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


        filteredList = new ArrayList<>();

        foodItemArrayList = new ArrayList<>();
         /*
         foodAdapter = new FoodAdapter(foodItemArrayList, this);
    역할?어댑터에 데이터값을 보내주기 위해서 현재 위치도 보내야 되는 이유는 어디에서 데이터를 보내는지 알기 위해서
    책임?
    협력?
    */

        product_search_adapter = new product_search_adapter(this, foodItemArrayList,SharedPreferences_phone);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        product_search2.setLayoutManager(linearLayoutManager);
        product_search2.setAdapter(product_search_adapter);
        product_search2.setVisibility(View.GONE);


        product_search_adapter.setOnItemClickListener(new product_search_adapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, String data) {
                Intent intent = new Intent(product_search_result.this, product_search_result.class);
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });


        foodItemArrayList.add(new product_search_data("아이폰"));
        foodItemArrayList.add(new product_search_data("아이폰미니"));
        foodItemArrayList.add(new product_search_data("아이폰14"));
        foodItemArrayList.add(new product_search_data("아이폰 충천기"));
        foodItemArrayList.add(new product_search_data("14프로 케이스"));
        foodItemArrayList.add(new product_search_data("미개봉"));
        foodItemArrayList.add(new product_search_data("12미니 케이스"));
        product_search_adapter.notifyDataSetChanged();
        //1-----------------------------------------------------------------------------------------------------------------------------



         /*
         addTextChangedListener,new TextWatcher()
         why?왜 이름 addTextChangedListener일까 컴퓨터에게 에딕트텍스트안에 바뀐 데이터값을 추가한것
    역할?new TextWatcher()은 인터페이스로 전,현재,후 상황을 파악한다
    책임?
    협력?
    */
        product_search_result_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    product_search2.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);

                } else if(editable.length() > 1){
                    product_search2.setVisibility(View.VISIBLE);
                    String searchText = product_search_result_edittext.getText().toString();
                    searchFilter(searchText);
                  }
            }
        });


        product_search_result_최신순 = (Button) findViewById(R.id.product_search_result_최신순);
        product_search_result_정확도순 = (Button) findViewById(R.id.product_search_result_정확도순);
        product_search_result_가격 = (Button) findViewById(R.id.product_search_result_가격);
        product_search_result_최신순.setVisibility(View.GONE);

        product_search_result_최신순.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_product_search_result_최신순();
            }
        });


        product_search_result_정확도순.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_product_search_result_정확도순();
            }
        });

        product_search_result_가격.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_product_search_result_가격();
            }
        });
    }


    // INSERT 구문 은 WHERE 절을 지원하지 않으므로 쿼리가 실패합니다. id열이 고유하거나 기본 키 라고 가정합니다 .
    private void product_search_result(String result_data, String style_recent,int low_cost,int high_cost) {
        product_ApiInterface ApiInterface = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<List<product_data>> call = ApiInterface.product_search_result(result_data, style_recent,low_cost,high_cost);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new home_adapter(this, lists, itemClickListener);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        list = lists;
    }


    public void searchFilter(String searchText) {
         /*
         filteredList.clear();
    역할?검색한 내용 저장
    책임?검색한 내용을 저장을 해줘야 된다
    협력?filteredList.add(foodItemArrayList.get(i));에서 리스트를 계속 추가를 한다 쉽게 말해서 검색한 내용이 계속 저장 되어있으면 안된다
    */

        filteredList.clear();

        for (int i = 0; i < foodItemArrayList.size(); i++) {
             /*
              if (foodItemArrayList.get(i).getFoodName().toLowerCase().contains(searchText.toLowerCase())) {
    역할?리스트를 사이즈만큼 돌려서 getFoodName()값을 가져온다
    책임?
    협력?
    */
            if (foodItemArrayList.get(i).getProduct_name().contains(searchText)) {
                filteredList.add(foodItemArrayList.get(i));
            }
        }
        product_search_adapter.filterList(filteredList);
    }


    //다이얼로그 실행
    public void dialog_product_search_result_최신순() {
        product_search_result_최신순_정확순_dialog.show(); // 다이얼로그 띄우기
        /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */

        // 위젯 연결 방식은 각자 취향대로~
        // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
        // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.
        Button 최신순 = product_search_result_최신순_정확순_dialog.findViewById(R.id.product_search_최신순_팝업);
        Button 정확순 = product_search_result_최신순_정확순_dialog.findViewById(R.id.product_search_정확순_팝업);
            정확순.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    정확순.setTextColor(Color.parseColor("#DC9146"));
                    최신순.setTextColor(Color.parseColor("#EBFBFF"));
                    product_search_result_최신순.setVisibility(View.INVISIBLE);
                    product_search_result_정확도순.setVisibility(View.VISIBLE);
                    product_search_result(result_data, "정확순",0,0);
                    product_search_result_최신순_정확순_dialog.cancel();
                }
            });

            최신순.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    정확순.setTextColor(Color.parseColor("#EBFBFF"));
                    최신순.setTextColor(Color.parseColor("#DC9146"));
                    product_search_result_정확도순.setVisibility(View.INVISIBLE);
                    product_search_result_최신순.setVisibility(View.VISIBLE);
                    product_search_result(result_data, "최신순",0,0);
                    product_search_result_최신순_정확순_dialog.cancel();
                }
            });
    }

    //다이얼로그 실행
    public void dialog_product_search_result_정확도순() {
        product_search_result_최신순_정확순_dialog.show(); // 다이얼로그 띄우기
        /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */
        // 위젯 연결 방식은 각자 취향대로~
        // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
        // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.
        Button 최신순 = product_search_result_최신순_정확순_dialog.findViewById(R.id.product_search_최신순_팝업);
        Button 정확순 = product_search_result_최신순_정확순_dialog.findViewById(R.id.product_search_정확순_팝업);
        정확순.setTextColor(Color.parseColor("#DC9146"));
        정확순.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                정확순.setTextColor(Color.parseColor("#DC9146"));
                최신순.setTextColor(Color.parseColor("#EBFBFF"));
                product_search_result_최신순.setVisibility(View.INVISIBLE);
                product_search_result_정확도순.setVisibility(View.VISIBLE);
                product_search_result(result_data, "정확순",0,0);
                product_search_result_최신순_정확순_dialog.cancel();
            }
        });

        최신순.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                정확순.setTextColor(Color.parseColor("#EBFBFF"));
                최신순.setTextColor(Color.parseColor("#DC9146"));
                product_search_result_정확도순.setVisibility(View.INVISIBLE);
                product_search_result_최신순.setVisibility(View.VISIBLE);
                product_search_result(result_data, "최신순",0,0);
                product_search_result_최신순_정확순_dialog.cancel();
            }
        });
    }


    //다이얼로그 실행
    public void dialog_product_search_result_가격() {
        product_search_result_가격_dialog.show(); // 다이얼로그 띄우기
        /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */
        // 위젯 연결 방식은 각자 취향대로~
        // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
        // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.
        Button 취소하기 = product_search_result_가격_dialog.findViewById(R.id.product_search_result_cancel);
        Button 적용하기 = product_search_result_가격_dialog.findViewById(R.id.product_search_result_entire);

        EditText low_cost = product_search_result_가격_dialog.findViewById(R.id.product_search_result_low_cost);
        EditText high_cost = product_search_result_가격_dialog.findViewById(R.id.product_search_result_high_cost);


        취소하기.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product_search_result_가격_dialog.cancel();
            }
        });

        적용하기.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String low_cost_edittext = low_cost.getText().toString();
                String high_cost_edittext = high_cost.getText().toString();

                int low_cost_edittext_integer =  Integer.parseInt(low_cost_edittext);
                int high_cost_edittext_integer =  Integer.parseInt(high_cost_edittext);

                product_search_result(result_data, null,low_cost_edittext_integer,high_cost_edittext_integer);
                product_search_result_가격_dialog.cancel();
            }
        });
    }
}